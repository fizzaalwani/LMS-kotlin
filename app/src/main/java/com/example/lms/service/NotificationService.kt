package com.example.lms.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.lms.MainActivity
import com.example.lms.R

/**
 * Background service for sending course notifications
 */
class NotificationService : Service() {

    private val TAG = "NotificationService"
    private val CHANNEL_ID = "lms_notifications"
    private val NOTIFICATION_ID = 1001

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "NotificationService created")
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "NotificationService started")

        val title = intent?.getStringExtra("title") ?: "LMS Update"
        val message = intent?.getStringExtra("message") ?: "Check out new courses!"
        val courseId = intent?.getStringExtra("courseId")

        sendNotification(title, message, courseId)

        // Stop service after sending notification
        stopSelf()

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "LMS Notifications"
            val descriptionText = "Notifications for courses and updates"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d(TAG, "Notification channel created")
        }
    }

    private fun sendNotification(title: String, message: String, courseId: String?) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            courseId?.let { putExtra("courseId", it) }
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)

        Log.d(TAG, "Notification sent: $title - $message")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "NotificationService destroyed")
    }

    companion object {
        fun startNotification(context: Context, title: String, message: String, courseId: String? = null) {
            val intent = Intent(context, NotificationService::class.java).apply {
                putExtra("title", title)
                putExtra("message", message)
                courseId?.let { putExtra("courseId", it) }
            }
            context.startService(intent)
        }
    }
}