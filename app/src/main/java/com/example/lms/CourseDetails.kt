package com.example.lms

//
//import android.os.Bundle
//import android.util.Log
//import android.widget.TextView
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.lms.api.ApiClient
//import com.example.lms.model.Course
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class CourseDetails: AppCompatActivity() {
//
//    private lateinit var tvCourseTitle: TextView
//    private lateinit var tvCourseSubtitle: TextView
//    private lateinit var tvCourseCategory: TextView
//    private lateinit var tvCourseLevel: TextView
//    private lateinit var tvCourseLanguage: TextView
//    private lateinit var tvCoursePrice: TextView
//    private lateinit var tvCourseDescription: TextView
//    private lateinit var tvCourseObjectives: TextView
//    private lateinit var tvCourseWelcome: TextView
//    private lateinit var tvInstructorName: TextView
//    private lateinit var tvInstructorEmail: TextView
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_course_details)
//
//        // Initialize all TextViews
//        tvCourseTitle = findViewById(R.id.tvCourseTitle)
//        tvCourseSubtitle = findViewById(R.id.tvCourseSubtitle)
//        tvCourseCategory = findViewById(R.id.tvCourseCategory)
//        tvCourseLevel = findViewById(R.id.tvCourseLevel)
//        tvCourseLanguage = findViewById(R.id.tvCourseLanguage)
//        tvCoursePrice = findViewById(R.id.tvCoursePrice)
//        tvCourseDescription = findViewById(R.id.tvCourseDescription)
//        tvCourseObjectives = findViewById(R.id.tvCourseObjectives)
//        tvCourseWelcome = findViewById(R.id.tvCourseWelcome)
//        tvInstructorName = findViewById(R.id.tvInstructorName)
//        tvInstructorEmail = findViewById(R.id.tvInstructorEmail)
//
//        val courseId = intent.getStringExtra("courseId")
//
//        if (courseId == null) {
//            Log.e("CourseDetails", "Course ID is null!")
//            Toast.makeText(this, "Error: Course ID not found", Toast.LENGTH_SHORT).show()
//            finish()
//            return
//        }
//
//        Log.d("CourseDetails", "Fetching course with ID: $courseId")
//        fetchCourseDetails(courseId)
//    }
//
//    private fun fetchCourseDetails(courseId: String) {
//        ApiClient.apiService.getCourseDetails(courseId)
//            .enqueue(object : Callback<Course> {
//                override fun onResponse(call: Call<Course>, response: Response<Course>) {
//                    if (response.isSuccessful) {
//                        val course = response.body()
//                        if (course != null) {
//                            Log.d("CourseDetails", "Course fetched successfully: ${course.title}")
//                            displayCourseDetails(course)
//                        } else {
//                            Log.e("CourseDetails", "Course body is null")
//                            Toast.makeText(this@CourseDetails, "No course data found", Toast.LENGTH_SHORT).show()
//                        }
//                    } else {
//                        Log.e("CourseDetails", "Response failed: ${response.code()} ${response.message()}")
//                        Log.e("CourseDetails", "Error body: ${response.errorBody()?.string()}")
//                        Toast.makeText(this@CourseDetails, "Failed to load course: ${response.code()}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<Course>, t: Throwable) {
//                    Log.e("CourseDetails", "API call failed", t)
//                    Toast.makeText(this@CourseDetails, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
//                }
//            })
//    }
//
//    private fun displayCourseDetails(course: Course) {
//        // Set all the data to views
//        tvCourseTitle.text = course.title ?: "No Title"
//        tvCourseSubtitle.text = course.subtitle ?: "No subtitle available"
//        tvCourseCategory.text = course.category ?: "N/A"
//        tvCourseLevel.text = course.level ?: "N/A"
//        tvCourseLanguage.text = course.language ?: "N/A"
//        tvCoursePrice.text = "$${course.price ?: 0}"
//        tvCourseDescription.text = course.description ?: "No description available"
//        tvCourseObjectives.text = course.objectives ?: "No objectives specified"
//        tvCourseWelcome.text = course.welcomeMessage ?: "Welcome to this course!"
//        tvInstructorName.text = course.instructorName ?: "Unknown Instructor"
//        tvInstructorEmail.text = course.instructorEmail ?: "No email provided"
//    }
//}


import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lms.api.ApiClient
import com.example.lms.model.Course
import com.example.lms.model.CourseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseDetails: AppCompatActivity() {

    private val TAG = "CourseDetails"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Log.d(TAG, "onCreate started")
            setContentView(R.layout.activity_course_details)
            Log.d(TAG, "Layout set successfully")

            // Get course ID from intent
            val courseId = intent.getStringExtra("courseId")
            Log.d(TAG, "Received courseId: $courseId")

            if (courseId == null || courseId.isEmpty()) {
                Log.e(TAG, "Course ID is null or empty!")
                Toast.makeText(this, "Error: Course ID not found", Toast.LENGTH_LONG).show()
                finish()
                return
            }

            // Initialize views with loading text
            initializeViews()

            // Fetch course details
            fetchCourseDetails(courseId)

        } catch (e: Exception) {
            Log.e(TAG, "Error in onCreate", e)
            Toast.makeText(this, "Error loading course: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun initializeViews() {
        try {
            Log.d(TAG, "Initializing views...")

            findViewById<TextView>(R.id.tvCourseTitle).text = "Loading..."
            findViewById<TextView>(R.id.tvCourseSubtitle).text = "Please wait..."
            findViewById<TextView>(R.id.tvCourseCategory).text = "..."
            findViewById<TextView>(R.id.tvCourseLevel).text = "..."
            findViewById<TextView>(R.id.tvCourseLanguage).text = "..."
            findViewById<TextView>(R.id.tvCoursePrice).text = "$0"
            findViewById<TextView>(R.id.tvCourseDescription).text = "Loading description..."
            findViewById<TextView>(R.id.tvCourseObjectives).text = "Loading objectives..."
            findViewById<TextView>(R.id.tvCourseWelcome).text = "Loading welcome message..."
            findViewById<TextView>(R.id.tvInstructorName).text = "Loading instructor..."
            findViewById<TextView>(R.id.tvInstructorEmail).text = ""

            Log.d(TAG, "All views initialized successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing views", e)
            throw e
        }
    }

    private fun fetchCourseDetails(courseId: String) {
        Log.d(TAG, "Fetching course details for ID: $courseId")

        try {
            // Now expecting CourseResponse instead of Course
            ApiClient.apiService.getCourseDetails(courseId)
                .enqueue(object : Callback<CourseResponse> {
                    override fun onResponse(call: Call<CourseResponse>, response: Response<CourseResponse>) {
                        Log.d(TAG, "API Response received")
                        Log.d(TAG, "Response code: ${response.code()}")
                        Log.d(TAG, "Response message: ${response.message()}")

                        if (response.isSuccessful) {
                            val courseResponse = response.body()
                            Log.d(TAG, "Response body: $courseResponse")

                            if (courseResponse != null && courseResponse.success) {
                                val course = courseResponse.data
                                Log.d(TAG, "Course data received: ${course.title}")
                                displayCourseDetails(course)
                            } else {
                                Log.e(TAG, "Course response is null or success=false")
                                runOnUiThread {
                                    Toast.makeText(
                                        this@CourseDetails,
                                        "Course not found",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            Log.e(TAG, "Response failed: ${response.code()}")
                            Log.e(TAG, "Error body: $errorBody")
                            runOnUiThread {
                                Toast.makeText(
                                    this@CourseDetails,
                                    "Failed to load course: ${response.code()}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<CourseResponse>, t: Throwable) {
                        Log.e(TAG, "API call failed", t)
                        Log.e(TAG, "Error message: ${t.message}")
                        Log.e(TAG, "Error cause: ${t.cause}")
                        runOnUiThread {
                            Toast.makeText(
                                this@CourseDetails,
                                "Network error: ${t.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
        } catch (e: Exception) {
            Log.e(TAG, "Error making API call", e)
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayCourseDetails(course: Course) {
        try {
            Log.d(TAG, "Displaying course details")
            Log.d(TAG, "Title: ${course.title}")
            Log.d(TAG, "Category: ${course.category}")
            Log.d(TAG, "Price: ${course.price}")

            runOnUiThread {
                findViewById<TextView>(R.id.tvCourseTitle).text =
                    course.title ?: "No Title"

                findViewById<TextView>(R.id.tvCourseSubtitle).text =
                    course.subtitle ?: "No subtitle available"

                findViewById<TextView>(R.id.tvCourseCategory).text =
                    course.category ?: "N/A"

                findViewById<TextView>(R.id.tvCourseLevel).text =
                    course.level ?: "N/A"

                findViewById<TextView>(R.id.tvCourseLanguage).text =
                    course.language ?: "N/A"

                findViewById<TextView>(R.id.tvCoursePrice).text =
                    "$${course.price ?: 0}"

                findViewById<TextView>(R.id.tvCourseDescription).text =
                    course.description ?: "No description available"

                findViewById<TextView>(R.id.tvCourseObjectives).text =
                    course.objectives ?: "No objectives specified"

                findViewById<TextView>(R.id.tvCourseWelcome).text =
                    course.welcomeMessage ?: "Welcome to this course!"

                findViewById<TextView>(R.id.tvInstructorName).text =
                    course.instructorName ?: "Unknown Instructor"

                findViewById<TextView>(R.id.tvInstructorEmail).text =
                    course.instructorEmail ?: "No email provided"

                Log.d(TAG, "Course details displayed successfully")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error displaying course details", e)
            runOnUiThread {
                Toast.makeText(this, "Error displaying data: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy called")
        super.onDestroy()
    }
}