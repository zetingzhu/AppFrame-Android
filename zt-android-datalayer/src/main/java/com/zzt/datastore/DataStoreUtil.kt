package com.zzt.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zzt.zt_android_datalayer.MyApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

/**
 * @author: zeting
 * @date: 2025/4/7
 *
 */
object DataStoreUtil {
    val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    // 在 Application 类中定义
    val dataStore: DataStore<Preferences> = MyApplication.context.userDataStore


    /**
     *
     * 异步获取数据
     * */
    fun <Value> getData(key: String, defaultValue: Value): Value {
        val result = when (defaultValue) {
            is Int -> readIntData(key, defaultValue)
            is Float -> readFloatData(key, defaultValue)
            is Double -> readDoubleData(key, defaultValue)
            is Boolean -> readBoolean(key, defaultValue)
            is String -> readString(key, defaultValue)
            is Long -> readLong(key, defaultValue)

            else -> throw IllegalArgumentException("can not find the $key type")
        }

        return result as Value
    }

    /**
     * 异步输入数据
     */
    fun <Value> putData(key: String, value: Value) {
        when (value) {
            is Int -> saveIntData(key, value)
            is Long -> saveLongData(key, value)
            is Float -> saveFloatData(key, value)
            is Double -> saveDoubleData(key, value)
            is Boolean -> saveBoolean(key, value)
            is String -> saveString(key, value)
            else -> throw IllegalArgumentException("unSupport $value type !!!")
        }
    }

    /**
     * 移除指定 Key 的值
     * @param key 要移除的 Key
     */
    suspend fun remove(key: Preferences.Key<*>) {
        dataStore.edit { preferences ->
            preferences.remove(key)
        }
    }

    /**
     * 清空所有保存的值
     */
    suspend fun clear() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private fun saveString(key: String, value: String) = runBlocking {
        saveSyncString(key, value)
    }

    private suspend fun saveSyncString(key: String, value: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[stringPreferencesKey(key)] = value
        }
    }

    private fun saveBoolean(key: String, value: Boolean) =
        runBlocking { saveSyncBoolean(key, value) }

    private suspend fun saveSyncBoolean(key: String, value: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[booleanPreferencesKey(key)] = value
        }
    }

    private fun saveDoubleData(key: String, value: Double) = runBlocking {
        saveSyncDoubleData(key, value)
    }

    private suspend fun saveSyncDoubleData(key: String, value: Double) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[doublePreferencesKey(key)] = value
        }
    }

    private fun saveFloatData(key: String, value: Float) =
        runBlocking { saveSyncFloatData(key, value) }

    private suspend fun saveSyncFloatData(key: String, value: Float) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[floatPreferencesKey(key)] = value
        }
    }

    private fun saveLongData(key: String, value: Long) =
        runBlocking { saveSyncLongData(key, value) }

    private suspend fun saveSyncLongData(key: String, value: Long) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[longPreferencesKey(key)] = value
        }
    }

    private fun saveIntData(key: String, value: Int) = runBlocking { saveSyncIntData(key, value) }

    private suspend fun saveSyncIntData(key: String, value: Int) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[intPreferencesKey(key)] = value
        }
    }

    private fun readIntData(key: String, defaultValue: Int): Int {
        var resultValue = defaultValue

        runBlocking {
            dataStore.data.first {
                resultValue = it[intPreferencesKey(key)] ?: resultValue
                true
            }
        }

        return resultValue
    }

    private fun readFloatData(key: String, defaultValue: Float): Float {
        var resultValue = defaultValue

        runBlocking {
            dataStore.data.first {
                resultValue = it[floatPreferencesKey(key)] ?: resultValue
                true
            }
        }

        return resultValue
    }

    private fun readDoubleData(key: String, defaultValue: Double): Double {
        var resultValue = defaultValue

        runBlocking {
            dataStore.data.first {
                resultValue = it[doublePreferencesKey(key)] ?: resultValue
                true
            }
        }

        return resultValue
    }

    private fun readBoolean(key: String, defaultValue: Boolean): Boolean {
        var resultValue = defaultValue

        runBlocking {
            dataStore.data.first {
                resultValue = it[booleanPreferencesKey(key)] ?: resultValue
                true
            }
        }

        return resultValue
    }

    private fun readString(key: String, defaultValue: String): String {
        var resultValue = defaultValue

        runBlocking {
            dataStore.data.first {
                resultValue = it[stringPreferencesKey(key)] ?: defaultValue

                true
            }
        }

        return resultValue
    }

    private fun readLong(key: String, defaultValue: Long): Long {
        var resultValue = defaultValue

        runBlocking {
            dataStore.data.first {
                resultValue = it[longPreferencesKey(key)] ?: resultValue
                true
            }
        }

        return resultValue

    }

}