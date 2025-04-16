package com.zzt.recevier;

import android.content.Context;

import com.echatsoft.echatsdk.broadcast.EChatLocalMessageReceiver;
import com.echatsoft.echatsdk.core.model.SDKMessage;

/**
 * 作者:ocean
 * 时间:2020/7/17 2:43 PM
 * 用途:echat本地消息推送
 */
public class EChatLocalMessageRecevier extends EChatLocalMessageReceiver {
    private static final int UI_UNREAD_COUNT_CHANGE = 11111;

    @Override
    public void unreadCountChange(Context context, int unreadCount, int companyId, long tm) {

    }

    @Override
    public void receiveNewMsg(Context context, SDKMessage message) {
        //下执行的一些方法 为耗时操作
    }


}
