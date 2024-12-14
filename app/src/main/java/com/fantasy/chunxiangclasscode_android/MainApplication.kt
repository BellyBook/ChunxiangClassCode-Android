package com.fantasy.chunxiangclasscode_android

import android.app.Application

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        println("MainApplication onCreate")
    }
}