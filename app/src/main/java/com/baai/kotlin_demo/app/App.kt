package com.baai.kotlin_demo.app

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

class App : Application() {

    companion object{
        val TAG = "wan_android"
         //上下文设置成只读的
         var context :Context by Delegates.notNull()
            private set
         //延迟初始化
        lateinit var instance:Application


    }
}