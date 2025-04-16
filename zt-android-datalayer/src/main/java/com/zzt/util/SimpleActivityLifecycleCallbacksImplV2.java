package com.zzt.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zzt.datastore.MultiProcessDataStoreUtil;


/**
 * @author: zeting
 * @date: 2025/04/15
 * 监听Activity生命周期ActivityLifecycleCallbacks
 * 多进程方案
 */
public class SimpleActivityLifecycleCallbacksImplV2 implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = SimpleActivityLifecycleCallbacksImplV2.class.getSimpleName();

    private static class InnerClass {
        private static final SimpleActivityLifecycleCallbacksImplV2 INSTANCE = new SimpleActivityLifecycleCallbacksImplV2();
    }

    private SimpleActivityLifecycleCallbacksImplV2() {
    }

    public static SimpleActivityLifecycleCallbacksImplV2 getInstance() {
        return InnerClass.INSTANCE;
    }

    private boolean mIsBackground = false; //是否在后台
    private Integer mForegroundCount = 0;


    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

        mForegroundCount = MultiProcessDataStoreUtil.INSTANCE.getData();
        Log.i(TAG, "LifecycleActivity Start old count:" + mForegroundCount);


        if (mForegroundCount != null) {
            ++mForegroundCount;
            MultiProcessDataStoreUtil.INSTANCE.putData(mForegroundCount);

            Log.d(TAG, "LifecycleActivity Start activity:" + activity + " count:" + mForegroundCount + " 获取进程名字:" + SystemUtil.getProcessName(activity));
        }
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if (mIsBackground) {
            mIsBackground = false;
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        mForegroundCount = MultiProcessDataStoreUtil.INSTANCE.getData();
        if (mForegroundCount != null) {
            --mForegroundCount;

            MultiProcessDataStoreUtil.INSTANCE.putData(mForegroundCount);

            Log.d(TAG, "LifecycleActivity Stop activity:" + activity + " count:" + mForegroundCount + " 获取进程名字:" + SystemUtil.getProcessName(activity));

            if (mForegroundCount <= 0) {
                mIsBackground = true;

                //  当应用在后台，执行切换启动图标方案
//                IconSwitcher.getInstance().backSwitchIcon(activity);
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    /**
     * 是否在后台
     *
     * @return true 在后台  false不在后台
     */
    public boolean isBackground() {
        return mIsBackground;
    }
}
