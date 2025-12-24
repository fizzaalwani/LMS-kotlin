package com.example.lms


//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.lms.api.ApiClient
//import com.example.lms.model.Course
//import com.example.lms.model.CoursesResponse
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response

//class TeacherDashboard : AppCompatActivity() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: CoursesAdapter
//    private var courses: List<Course> = listOf()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_teacher_dasboard)
//
//        val btnAddCourse = findViewById<Button>(R.id.btnAddCourse)
//        val btnLogout = findViewById<Button>(R.id.btnLogout)
//        recyclerView = findViewById(R.id.rvTeacherCourses)
//
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        adapter = CoursesAdapter(courses) { course ->
//            // Navigate to CourseDetails
//            val intent = Intent(this, CourseDetails::class.java)
//            intent.putExtra("courseId", course._id)
//            startActivity(intent)
//        }
//        recyclerView.adapter = adapter
//
//        btnAddCourse.setOnClickListener {
//            startActivity(Intent(this, AddCourse::class.java))
//        }
//
//        btnLogout.setOnClickListener {
//            // your session logout logic
//            finish()
//        }
//
//        fetchCourses()
//    }
//
//    private fun fetchCourses() {
//        ApiClient.apiService.getAllCourses().enqueue(object : Callback<CoursesResponse> {
//            override fun onResponse(
//                call: Call<CoursesResponse>,
//                response: Response<CoursesResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val coursesList = response.body()?.data ?: listOf()
//                    adapter.updateCourses(coursesList)
//                } else {
//                    Toast.makeText(this@TeacherDashboard, "Failed to fetch courses", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<CoursesResponse>, t: Throwable) {
//                Toast.makeText(this@TeacherDashboard, t.message, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//}

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.api.ApiClient
import com.example.lms.model.Course
import com.example.lms.model.CoursesResponse
import com.example.lms.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherDashboard : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CoursesAdapter
    private var courses: List<Course> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_dasboard)

        val btnAddCourse = findViewById<Button>(R.id.btnAddCourse)
        val btnLogout = findViewById<Button>(R.id.btnLogout)
        recyclerView = findViewById(R.id.rvTeacherCourses)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CoursesAdapter(courses) { course ->
            // Log the course data to debug
            Log.d("TeacherDashboard", "Course clicked: ${course.title}, ID: ${course.id}")

            // Check if ID is not null before navigating
            if (course.id != null) {
                val intent = Intent(this, CourseDetails::class.java)
                intent.putExtra("courseId", course.id)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Course ID is missing", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter

        btnAddCourse.setOnClickListener {
            startActivity(Intent(this, AddCourse::class.java))
        }

        btnLogout.setOnClickListener {
            val session = SessionManager(this)
            session.logout()
            finish()
        }

        fetchCourses()
    }

    private fun fetchCourses() {
        ApiClient.apiService.getAllCourses().enqueue(object : Callback<CoursesResponse> {
            override fun onResponse(
                call: Call<CoursesResponse>,
                response: Response<CoursesResponse>
            ) {
                if (response.isSuccessful) {
                    val coursesList = response.body()?.data ?: listOf()
                    Log.d("TeacherDashboard", "Fetched ${coursesList.size} courses")

                    // Log first course to check data
                    if (coursesList.isNotEmpty()) {
                        Log.d("TeacherDashboard", "First course: ${coursesList[0].title}, ID: ${coursesList[0].id}")
                    }

                    adapter.updateCourses(coursesList)
                } else {
                    Log.e("TeacherDashboard", "Response failed: ${response.code()} ${response.message()}")
                    Toast.makeText(this@TeacherDashboard, "Failed to fetch courses", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CoursesResponse>, t: Throwable) {
                Log.e("TeacherDashboard", "API call failed", t)
                Toast.makeText(this@TeacherDashboard, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}