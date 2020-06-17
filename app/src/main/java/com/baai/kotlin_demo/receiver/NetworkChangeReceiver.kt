package com.baai.kotlin_demo.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.baai.kotlin_demo.constant.Constant
import com.baai.kotlin_demo.event.NetworkChangeEvent
import com.baai.kotlin_demo.utils.NetWorkUtil
import com.baai.kotlin_demo.utils.Preferences
import org.greenrobot.eventbus.EventBus

class NetworkChangeReceiver :BroadcastReceiver(){
    /**
     * 缓存上一次的网络状态
     */
private var hasNetwork by Preferences(Constant.HAS_NETWORK_KEY, true)

    override fun onReceive(context: Context, intent: Intent?) {
        val isConnected = NetWorkUtil.isNetworkConnected(context)
        if (isConnected) {
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(NetworkChangeEvent(isConnected))
            }
        } else {
            EventBus.getDefault().post(NetworkChangeEvent(isConnected))
        }

    }
}