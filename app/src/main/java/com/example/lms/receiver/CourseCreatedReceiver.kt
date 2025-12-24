package com.example.lms.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.lms.service.NotificationService

class CourseCreatedReceiver : BroadcastReceiver() {

    private val TAG = "CourseCreatedReceiver"

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent == null) return

        val action = intent.action
        if (action == "com.example.lms.COURSE_ADDED") {
            val courseName = intent.getStringExtra("course_name") ?: "New Course"
            val courseId = intent.getStringExtra("course_id")

            Log.d(TAG, "Received course created broadcast: $courseName")

            // Start notification service
            NotificationService.startNotification(
                context,
                title = "New Course Added",
                message = "Course \"$courseName\" is now available!",
                courseId = courseId
            )
        }
    }
}
