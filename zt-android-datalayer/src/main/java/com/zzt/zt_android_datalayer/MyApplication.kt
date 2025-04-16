package com.zzt.zt_android_datalayer

import android.app.Application
import android.content.Context
import android.content.pm.ActivityInfo
import android.util.Log
import com.echatsoft.echatsdk.core.EChatSDK
import com.echatsoft.echatsdk.core.utils.FixWebView
import com.echatsoft.echatsdk.utils.pub.EChatCustoms
import com.zzt.datastore.MultiProcessDataStoreUtil
import com.zzt.util.SimpleActivityLifecycleCallbacksImplV2
import com.zzt.util.SystemUtil

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

        const val TAG = "MyApplication"

        // echat 环境切换域名
        var ECHAT_HOST_URL: String = "https://id.echatsoft.com"
        const val ECHATSDK_APPID: String = "SDKYSPZWYOFKRIRFKFJ"
        const val ECHATSDK_APPSECRET: String = "P5JCYAXOAMSSH97QUXZG4QJNCCHRXGHSVFGXHANA3WR"
        const val ECHATSDK_COMPANYID: Long = 532464
        const val ECHATSDK_SERVERAPPID: String = "EB68F3A38E206CCD420703D6ED6B483B"
        const val ECHATSDK_SERVERTOKEN: String = "WHJ9kYY4"
    }

    override fun onCreate() {
        super.onCreate()

        val processName: String = SystemUtil.getProcessName(this)

        if (checkProcess(this)) {
            MultiProcessDataStoreUtil.putData(0 )
        }

        Log.e(TAG, "LifecycleActivity MyApplication  ::: " + MultiProcessDataStoreUtil.getData())

        // 配置服务器地址
        // 务必早于SDK初始化
        // 请勿做任何限制 符合隐私等合规
        // 不会启动SDK
        // hostUrl请联系技术支持 确定具体地址 雅加达环境 https://id.echatsoft.com
        EChatCustoms.setHostUrl(ECHAT_HOST_URL)

        //160 Echat 新版本 初始化SDK
        EChatSDK.init(
            this,
            ECHATSDK_APPID,
            ECHATSDK_APPSECRET,
            ECHATSDK_SERVERTOKEN,
            ECHATSDK_SERVERAPPID,
            ECHATSDK_COMPANYID
        )

        // 关闭默认本地消息推送(弹出通知)
        EChatSDK.getInstance().disableDefaultNotification(true)

        //配置横竖屏
        EChatSDK.getInstance().setOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        //Echat 异常配置 Using WebView from more than one process at once with the same data directory is not supported.
        FixWebView.fixOtherProcess(processName)

        registerActivityLifecycleCallbacks(SimpleActivityLifecycleCallbacksImplV2.getInstance())

    }


    /**
     * 对进程名字进行校验
     *
     * @param context
     * @return
     */
    fun checkProcess(context: MyApplication): Boolean {
        try {
            val processName: String = SystemUtil.getProcessName(context)
            val pkName = context.packageName
            return processName == pkName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

}