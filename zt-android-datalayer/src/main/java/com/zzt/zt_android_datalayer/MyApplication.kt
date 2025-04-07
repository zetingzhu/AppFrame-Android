package com.zzt.zt_android_datalayer

import android.app.Application
import android.content.Context

/**
 * @author: zeting
 * @date: 2025/4/7
 *
 */
class MyApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun instance(): MyApplication {
            return instance
                ?: throw IllegalStateException("MyApplication has not been initialized.")
        }

        /**
         *  提供 Context 的便捷访问
         */
        val context: Context
            get() = instance()
    }

}