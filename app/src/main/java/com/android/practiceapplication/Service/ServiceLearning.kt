package com.android.practiceapplication.Service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.android.practiceapplication.R

class ServiceLearning : Service() {
    private  val TAG = "Service Class"
    companion object {
        const val NOTIFICATION_ID = 1
    }
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
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
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
        startForegroundService()
        return super.onStartCommand(intent, flags, startId)
    }
    private fun startForegroundService() {
        val notification = createNotification()
        //here we are creating foreground service
        startForeground(NOTIFICATION_ID, notification)
    }
    private fun createNotification(): Notification {
        val notificationChannelId = "MY_SERVICE_CHANNEL"

            val channel = NotificationChannel(
                notificationChannelId,
                "My Foreground Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)


        return NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Service Running")
            .setContentText("Your service is running in the foreground")
            .setSmallIcon(R.drawable.ic_service_icon) // Replace with your icon
            .build()
    }


    inner class LocalBinder: Binder() {
        fun getService() : ServiceLearning{
            return this@ServiceLearning
        }
    }

    override fun onTimeout(startId: Int) {
        Log.d(TAG, "onTimeout: $startId")
        super.onTimeout(startId)
    }

}
