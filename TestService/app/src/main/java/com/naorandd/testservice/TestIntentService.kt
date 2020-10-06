package com.naorandd.testservice

import android.app.IntentService
import android.content.Intent
import android.util.Log


class TestIntentService : IntentService("TestIntentService") {
    override fun onCreate() {
        super.onCreate()
        Log.d("debug", "onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d("debug", "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d("debug", "onHandleIntent")
        val count = 10
        try {
            for (i in 0 until count) {
                Thread.sleep(1000)
                Log.d("debug", "sleep: $i")
            }
        } catch (e: InterruptedException) {
            Thread.currentThread().interrupt()
        }
    }

    override fun onDestroy() {
        Log.d("debug", "onDestroy")
        super.onDestroy()
    }

    init {
        Log.d("debug", "TestIntentService")
    }
}