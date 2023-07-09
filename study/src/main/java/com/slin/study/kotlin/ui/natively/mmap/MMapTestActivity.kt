package com.slin.study.kotlin.ui.natively.mmap

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dylanc.longan.context
import com.slin.core.logger.logd
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.theme.ApplicationTheme
import java.util.Locale

class MMapTestActivity : BaseActivity() {
    companion object {
        init {
            System.loadLibrary("native-mmap-test")
        }
    }

    private val mMMapClient = MMapClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setShowBackButton(true)

        mMMapClient.init(this)

        setContent {
            ApplicationTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }

    @Composable
    fun Content() {
        nativeOpenShm(context.getExternalFilesDir("")!!.absolutePath + "shm/test")
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS", Locale.getDefault())
        var testText by remember { mutableStateOf(nativeReadText()) }
        var serverText by remember { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Button(onClick = {
                nativeWriteText(format.format(Calendar.getInstance().time))
            }) {
                Text(text = "Write DataTime")
            }
            Button(onClick = {
                testText = nativeReadText()
            }) {
                Text(text = "Read($testText)")
            }

            Button(onClick = {
                val shm = nativeGetShareMemory()
                logd { "shm: $shm" }
                mMMapClient.passShm(shm.fd, shm.addr, shm.size)
            }) {
                Text(text = "Server PassShm")
            }
            Button(onClick = {
                serverText = mMMapClient.readText()
            }) {
                Text(text = "Server Read($serverText)")
            }
            Button(onClick = {
                mMMapClient.writeText("Server Data")
            }) {
                Text(text = "Server Write(Server Data)")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        nativeCloseShm()
    }

    private external fun nativeOpenShm(filePath: String)
    private external fun nativeWriteText(text: String)
    private external fun nativeReadText(): String
    private external fun nativeCloseShm()
    private external fun nativeGetShareMemory(): ShareMemory
}