package com.example.lms

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lms.api.ApiClient
import com.example.lms.model.Course
import com.example.lms.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AddCourse : AppCompatActivity() {

    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        session = SessionManager(this)

        // Find views
        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etCategory = findViewById<EditText>(R.id.etCategory)
        val etLevel = findViewById<EditText>(R.id.etLevel)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val etSubtitle = findViewById<EditText>(R.id.etSubtitle)
        val etObjectives = findViewById<EditText>(R.id.etObjectives)
        val etLanguage = findViewById<EditText>(R.id.etLanguage)
        val etWelcome = findViewById<EditText>(R.id.etWelcome)
        val etImage = findViewById<EditText>(R.id.etImage)
        val etPrice = findViewById<EditText>(R.id.etPrice)
        val btnAdd = findViewById<Button>(R.id.btnAddCourse)

        btnAdd.setOnClickListener {

            // Validate required fields
            val title = etTitle.text.toString().trim()
            val category = etCategory.text.toString().trim()
            val level = etLevel.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val price = etPrice.text.toString().trim()

            if (title.isEmpty() || category.isEmpty() || level.isEmpty() || description.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Safely parse price
            val priceInt = price.toIntOrNull() ?: 0

            // Optional fields
            val subtitle = etSubtitle.text.toString().trim()
            val objectives = etObjectives.text.toString().trim()
            val language = etLanguage.text.toString().trim()
            val welcome = etWelcome.text.toString().trim()
            val image = etImage.text.toString().trim()

            // Log debug info
            Log.d("ADD_COURSE_DEBUG", "ID=${session.getUserId()}")
            Log.d("ADD_COURSE_DEBUG", "NAME=${session.getUserName()}")
            Log.d("ADD_COURSE_DEBUG", "EMAIL=${session.getUserEmail()}")

            val course = Course(
                instructorId = session.getUserId(),
                instructorName = session.getUserName(),
                instructorEmail = session.getUserEmail(),
                date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                title = title,
                category = category,
                level = level,
                description = description,
                subtitle = subtitle,
                objectives = objectives,
                language = language,
                welcomeMessage = welcome,
                image = image,
                price = priceInt,
                isPublished = true
            )
            // After adding course to database, send broadcast
            val intent = Intent("com.example.lms.COURSE_ADDED")
            intent.putExtra("course_name", title)
            intent.putExtra("course_id", "course_123")
            sendBroadcast(intent)


            Log.d("ADD_COURSE_DEBUG", "COURSE OBJECT = $course")

            // Make API call
            ApiClient.apiService.addCourse(course)
                .enqueue(object : Callback<Course> {
                    override fun onResponse(call: Call<Course>, response: Response<Course>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@AddCourse,
                                "Course added successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            Log.e("ADD_COURSE_ERROR", "Response failed: ${response.code()} ${response.message()}")
                            Toast.makeText(
                                this@AddCourse,
                                "Failed to add course",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Course>, t: Throwable) {
                        Log.e("ADD_COURSE_ERROR", "API call failed", t)
                        Toast.makeText(
                            this@AddCourse,
                            "Error: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        }
    }
}