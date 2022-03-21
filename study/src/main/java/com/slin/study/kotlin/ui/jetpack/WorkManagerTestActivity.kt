package com.slin.study.kotlin.ui.jetpack

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.loader.app.LoaderManager
import androidx.loader.content.AsyncTaskLoader
import androidx.loader.content.Loader
import com.slin.core.logger.logd
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityWorkManagerTestBinding
import com.slin.study.kotlin.ui.testlist.TestListFragment

/**
 * author: slin
 * date: 2020/11/13
 * description:
 *
 */
class WorkManagerTestActivity : BaseActivity() {

    private lateinit var binding: ActivityWorkManagerTestBinding

    private val viewModel: WorkManagerTestViewModel by lazy {
        WorkManagerTestViewModel(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置标题
        intent?.extras?.getString(TestListFragment.INTENT_NAME)?.let {
            title = it
        }
        setShowBackButton(true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_manager_test)


        initView()

    }

    private fun initView() {
        binding.apply {
            viewModel.workResult.observe(this@WorkManagerTestActivity, Observer {
                tvWorkResult.text = it
            })
            btnOnceRequest.setOnClickListener { viewModel.onceRequestTest() }
            btnPeriodRequest.setOnClickListener { viewModel.periodRequestTest() }
            btnDownloadRequest.setOnClickListener { viewModel.downloadWorkTest() }
        }

        //测试Loader，这个与WorkManager无关
        LoaderManager.getInstance(this)
            .initLoader(1, Bundle.EMPTY, object : LoaderManager.LoaderCallbacks<String> {
                override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
                    logd { "onCreateLoader: $id" }
                    return object : AsyncTaskLoader<String>(this@WorkManagerTestActivity) {
                        override fun onStartLoading() {
                            super.onStartLoading()
                            logd { "onStartLoading: " }
                        }

                        override fun onStopLoading() {
                            super.onStopLoading()
                            logd { "onStopLoading: " }
                        }

                        override fun onCancelLoad(): Boolean {
                            return super.onCancelLoad()
                            logd { "onCancelLoad: " }
                        }

                        override fun loadInBackground(): String? {
                            logd { "loadInBackground: " }
                            Thread.sleep(1000)
                            return "This is result: 111"
                        }
                    }
                }

                override fun onLoadFinished(loader: Loader<String>, data: String?) {
                    logd { "onLoadFinished: $loader $data" }
                }

                override fun onLoaderReset(loader: Loader<String>) {
                    logd { "onLoaderReset: $loader" }
                }

            })
            .startLoading()
    }


}












