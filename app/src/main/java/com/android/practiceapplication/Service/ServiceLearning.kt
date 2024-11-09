package com.android.practiceapplication.Service

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlin.math.log
import kotlin.random.Random

class ServiceLearning : Service() {
    private  val TAG = "Service Class"

    override fun onCreate() {
        super.onCreate()
        application
        Log.d(TAG, "onCreate: ")
    }


    override fun onBind(p0: Intent?): IBinder {
        Log.d(TAG, "onBind: ")
        return LocalBinder()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        super.onDestroy()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind: ")
        return true
    }

    override fun onLowMemory() {
        Log.d(TAG, "onLowMemory: ")
        super.onLowMemory()
    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG, "onRebind: ")
        super.onRebind(intent)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        return super.onStartCommand(intent, flags, startId)
    }


    inner class LocalBinder: Binder() {
        fun getService() : ServiceLearning{
            return this@ServiceLearning
        }
    }

    fun getRandomNumber(from: Int=300, to: Int=600): Int {
        return Random.nextInt(from, to + 1)
    }

}
