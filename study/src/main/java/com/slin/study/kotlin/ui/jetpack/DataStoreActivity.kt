package com.slin.study.kotlin.ui.jetpack

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.google.protobuf.InvalidProtocolBufferException
import com.slin.study.kotlin.R
import com.slin.study.kotlin.base.BaseActivity
import com.slin.study.kotlin.databinding.ActivityDataStoreBinding
import com.slin.study.kotlin.proto.ProtoDataTestOuterClass.ProtoDataTest
import com.slin.study.kotlin.ui.testlist.TestListFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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
    private val preferenceStore by preferencesDataStore("preference_store_test")
    private val protoStore by dataStore(
        "proto.pb",
        serializer = object : Serializer<ProtoDataTest> {
            override suspend fun readFrom(input: InputStream): ProtoDataTest {
                try {
                    return ProtoDataTest.parseFrom(input)
                } catch (e: InvalidProtocolBufferException) {
                    throw CorruptionException("Cannot read proto.", e)
                }
            }

            override suspend fun writeTo(t: ProtoDataTest, output: OutputStream) {
                t.writeTo(output)
            }

            override val defaultValue: ProtoDataTest
                get() = ProtoDataTest.getDefaultInstance()

        })
    private val testDataKey = stringPreferencesKey("test_data_key")


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
                lifecycleScope.launchWhenCreated {
                    preferenceStore.edit { pf ->
                        pf[testDataKey] = etContent.text.toString()
                    }
                }
            }

            btnPreferenceRead.setOnClickListener {
                lifecycleScope.launchWhenCreated {
                    val value = preferenceStore.data.map { pf ->
                        pf[testDataKey] ?: ""
                    }.first()
                    etContent.setText(value)
                }
            }

            btnProtoSave.setOnClickListener {
                lifecycleScope.launchWhenCreated {
                    protoStore.updateData {
                        it.toBuilder()
                            .setAge(10)
                            .setName(etContent.text.toString())
                            .build()
                    }
                }
            }

            btnProtoRead.setOnClickListener {
                lifecycleScope.launchWhenCreated {
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