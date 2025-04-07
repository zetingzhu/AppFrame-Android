package com.zzt.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zzt.zt_android_datalayer.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * @author: zeting
 * @date: 2025/4/7
 *
 */
object DataStoreUtil {
    val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")
    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    // 在 Application 类中定义
    val dataStore: DataStore<Preferences> = MyApplication.context.userDataStore


    /**
     * 保存 Int 类型的值
     * @param key 键
     * @param value 值
     */
    suspend fun saveInt(key: Preferences.Key<Int>, value: Int) {
        dataStore?.edit { preferences ->
            preferences[key] = value
        }
    }

    /**
     * 读取 Int 类型的值
     * @param key 键
     * @param defaultValue 默认值
     * @return Flow<Int>
     */
    fun readInt(key: Preferences.Key<Int>, defaultValue: Int = 0): Flow<Int>? = dataStore?.data
        ?.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        ?.map { preferences ->
            preferences[key] ?: defaultValue
        }

    /**
     * 保存 Long 类型的值
     * @param key 键
     * @param value 值
     */
    suspend fun saveLong(key: Preferences.Key<Long>, value: Long) {
        dataStore?.edit { preferences ->
            preferences[key] = value
        }
    }

    /**
     * 读取 Long 类型的值
     * @param key 键
     * @param defaultValue 默认值
     * @return Flow<Long>
     */
    fun readLong(key: Preferences.Key<Long>, defaultValue: Long = 0L): Flow<Long> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[key] ?: defaultValue
        }

    /**
     * 保存 Float 类型的值
     * @param key 键
     * @param value 值
     */
    suspend fun saveFloat(key: Preferences.Key<Float>, value: Float) {
        dataStore?.edit { preferences ->
            preferences[key] = value
        }
    }

    /**
     * 读取 Float 类型的值
     * @param key 键
     * @param defaultValue 默认值
     * @return Flow<Float>
     */
    fun readFloat(key: Preferences.Key<Float>, defaultValue: Float = 0f): Flow<Float> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key] ?: defaultValue
            }

    /**
     * 保存 Boolean 类型的值
     * @param key 键
     * @param value 值
     */
    suspend fun saveBoolean(key: Preferences.Key<Boolean>, value: Boolean) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    /**
     * 读取 Boolean 类型的值
     * @param key 键
     * @param defaultValue 默认值
     * @return Flow<Boolean>
     */
    fun readBoolean(key: Preferences.Key<Boolean>, defaultValue: Boolean = false): Flow<Boolean> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key] ?: defaultValue
            }

    /**
     * 保存 String 类型的值
     * @param key 键
     * @param value 值
     */
    suspend fun saveString(key: Preferences.Key<String>, value: String) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    /**
     * 读取 String 类型的值
     * @param key 键
     * @param defaultValue 默认值
     * @return Flow<String?>
     */
    fun readString(key: Preferences.Key<String>, defaultValue: String? = null): Flow<String?> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key] ?: defaultValue
            }

    /**
     * 保存 StringSet 类型的值
     * @param key 键
     * @param value 值
     */
    suspend fun saveStringSet(key: Preferences.Key<Set<String>>, value: Set<String>) {
        dataStore.edit { preferences ->
            preferences[key] = value
        }
    }

    /**
     * 读取 StringSet 类型的值
     * @param key 键
     * @param defaultValue 默认值
     * @return Flow<Set<String>?>
     */
    fun readStringSet(
        key: Preferences.Key<Set<String>>,
        defaultValue: Set<String>? = null
    ): Flow<Set<String>?> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[key] ?: defaultValue
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

}