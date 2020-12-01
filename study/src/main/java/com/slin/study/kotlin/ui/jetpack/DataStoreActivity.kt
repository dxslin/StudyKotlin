package com.slin.study.kotlin.ui.jetpack

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.datastore.DataStore
import androidx.datastore.Serializer
import androidx.datastore.createDataStore
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityDataStoreBinding
import com.slin.study.kotlin.proto.ProtoDataTestOuterClass.ProtoDataTest
import com.slin.study.kotlin.ui.testlist.TestListFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream

/**
 * author: slin
 * date: 2020/5/6
 * description: data store 使用示例
 *
 *
 */
class DataStoreActivity : BaseActivity() {

    private lateinit var binding: ActivityDataStoreBinding
    private val preferenceStore = createDataStore("preference_store_test")
    private val protoStore: DataStore<ProtoDataTest> =
        createDataStore("proto.pb", serializer = object : Serializer<ProtoDataTest> {
            override fun readFrom(input: InputStream): ProtoDataTest {
                return ProtoDataTest.parseFrom(input)
            }

            override fun writeTo(t: ProtoDataTest, output: OutputStream) {
                t.writeTo(output)
            }

        })
    private val testDataKey = preferencesKey<String>("test_data_key")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_store)

        //设置标题
        intent?.extras?.getString(TestListFragment.INTENT_NAME)?.let {
            title = it
        }
        setShowBackButton(true)

        binding.apply {
            btnPreferenceSave.setOnClickListener {
                lifecycleScope.launch {
                    preferenceStore.edit { pf ->
                        pf[testDataKey] = etContent.text.toString()
                    }
                }
            }

            btnPreferenceRead.setOnClickListener {
                lifecycleScope.launch {
                    val value = preferenceStore.data.map { pf ->
                        pf[testDataKey] ?: ""
                    }.first()
                    etContent.setText(value)
                }
            }

            btnProtoSave.setOnClickListener {
                lifecycleScope.launch {
                    protoStore.updateData {
                        it.toBuilder()
                            .setAge(10)
                            .setName(etContent.text.toString())
                            .build()
                    }
                }
            }

            btnProtoRead.setOnClickListener {
                lifecycleScope.launch {
                    protoStore.data.collect {
                        etContent.setText(it.name)
                    }
                }
            }
            tvClearText.setOnClickListener {
                etContent.setText("")
            }


        }


    }


}