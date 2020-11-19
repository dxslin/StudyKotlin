package com.slin.study.kotlin.ui.jetpack

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.*
import androidx.work.*
import com.slin.study.kotlin.R
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


/**
 * author: slin
 * date: 2020/11/16
 * description:WorkManager使用测试
 * 1. WorkManager只能在app运行时运行任务，所以无法用来保活；
 * 2. 提交任务后，当时未执行任务的话，那么下次app启动时会在合适的时间自动执行任务
 * 3. 可以设置前台Notification当做前台任务执行，这样任务就不会被轻易杀死
 * 4. 如果任务执行过程中的app被杀死，下次启动会重新执行
 * 5. 周期任务最小执行间隔时间为15分钟
 */

private val TAG: String? = "WorkManagerTest"


class WorkManagerTestViewModel(val context: Context, private val owner: LifecycleOwner) :
    ViewModel() {

    private val workManager: WorkManager = WorkManager.getInstance(context)

    private val _workResult: MutableLiveData<String> = MutableLiveData()
    val workResult: LiveData<String> = _workResult

    private val inputWorkData = workDataOf(
        "name" to "slin",
        "msg" to "hi"
    )

    /**
     * 测试值调用一次的work
     */
    fun onceRequestTest() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)  //需要处于的网络连接状态
            .setRequiresBatteryNotLow(true)  //低电量不允许
            .setRequiresCharging(false)     //设置是否在充电
            .setRequiresDeviceIdle(true)    //设备是否空闲
            .build()

        val onceLogRequest = OneTimeWorkRequestBuilder<LogTestWorker>()
            .setInputData(inputWorkData)
            .setConstraints(constraints)
            .build()
        workManager.enqueue(onceLogRequest)

        val workInfoLiveData = workManager.getWorkInfoByIdLiveData(onceLogRequest.id)
        workInfoLiveData.observe(owner) {
            Log.d(TAG, "onceRequestTest: observeForever: ${it.state} ${it.outputData}")
        }

    }

    /**
     * 测试周期调用的work
     */
    fun periodRequestTest() {
        val periodRequest = PeriodicWorkRequestBuilder<LogTestWorker>(
            PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS,
            PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS, TimeUnit.MILLISECONDS
        )
            .setInputData(inputWorkData)
            .build()
        workManager.enqueue(periodRequest)
    }

    /**
     * 模拟下载测试work
     */
    fun downloadWorkTest() {
        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(
                workDataOf(
                    DownloadWorker.INPUT_URL to "http://www.baidu.com",
                    DownloadWorker.OUTPUT_FILE to "baidu.text"
                )
            )
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()
        workManager.enqueueUniqueWork("download task", ExistingWorkPolicy.APPEND, downloadRequest)
        workManager.getWorkInfoByIdLiveData(downloadRequest.id)
            .observe(owner, Observer {
                Log.d(TAG, "downloadWorkTest: $it")
            })
    }


    inner class LogTestWorker(val context: Context, workerParameters: WorkerParameters) :
        Worker(context, workerParameters) {


        override fun doWork(): Result {
            val data = work(inputData)
            return Result.success(workDataOf("result" to data))
        }
    }

    private fun work(inputData: Data): String {
        val name = inputData.getString("name")
        val msg = inputData.getString("msg")

        _workResult.postValue("start work")

        //模拟耗时处理
        try {
            Thread.sleep(10000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val data = "${System.currentTimeMillis()} -- doWork: hello $name, $msg"
        Log.d(TAG, "work: data = $data")

        _workResult.postValue(data)

        return data

    }
}


class DownloadWorker(context: Context, parameters: WorkerParameters) :
    CoroutineWorker(context, parameters) {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {
        val downloadUrl = inputData.getString(INPUT_URL) ?: return Result.failure()
        val outputFile = inputData.getString(OUTPUT_FILE) ?: return Result.failure()
        setForeground(createForegroundInfo("0%"))
        download(downloadUrl, outputFile)
        return Result.success()
    }

    private suspend fun download(downloadUrl: String, outputFile: String) {
        repeat(100) {
            delay(100)
            Log.d(TAG, "download: $downloadUrl $outputFile $it")
            setProgress(
                workDataOf(
                    "progress" to it
                )
            )
            setForeground(createForegroundInfo("$it%"))
        }
    }

    private fun createForegroundInfo(progress: String): ForegroundInfo {
        val id = "channel_id"
        val title = "Download File"
        val cancel = "Cancel"
        // This PendingIntent can be used to cancel the worker
        val intent = WorkManager.getInstance(applicationContext)
            .createCancelPendingIntent(getId())

        // Create a Notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setTicker(title)
            .setContentText(progress)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOngoing(true)
            // Add the cancel action to the notification which can
            // be used to cancel the worker
            .addAction(android.R.drawable.ic_delete, cancel, intent)
            .setChannelId(CHANNEL_ID)
            .build()
        return ForegroundInfo(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        // Create a Notification channel
        val channel =
            NotificationChannel(CHANNEL_ID, "download", NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel)
    }


    companion object {
        val INPUT_URL = "input_url"
        val OUTPUT_FILE = "output_file"
        private val CHANNEL_ID = "work manager test id"
    }

}
