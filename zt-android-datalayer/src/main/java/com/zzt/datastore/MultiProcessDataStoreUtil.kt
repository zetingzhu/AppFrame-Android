package com.zzt.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.MultiProcessDataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.zzt.zt_android_datalayer.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import androidx.datastore.dataStoreFile

/**
 * @author: zeting
 * @date: 2025/4/8
 * 多个进程可以交互的数据存储
 */

object MultiProcessDataStoreUtil {

    // 创建多进程数据存储实例
    val Context.multiProcessDataStore by lazy {
        MultiProcessDataStoreFactory.create(
            serializer = ProcessCountSerializer,
            scope = CoroutineScope(Dispatchers.IO),
            produceFile = { MyApplication.context.preferencesDataStoreFile(name = "multi_process_preferences") },
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { ProcessCountObj(0) }
            )
        )
    }


    // 在 Application 类中定义
    val multiDataStore = MyApplication.context.multiProcessDataStore


    fun putData(count: Int?) = runBlocking {
        if (count != null) {
            val obj = ProcessCountObj(count)
            multiDataStore.updateData { currentObj ->
                obj
            }
        }
    }


    fun getData(): Int? = runBlocking {
        val obj = multiDataStore.data.first()
        obj.mCount
    }

}
