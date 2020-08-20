package com.gailo22.module1

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class Module1Service : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
        Log.d("MODULE1", "onBind")
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

        Log.d("MODULE1", "onStartCommand")
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}