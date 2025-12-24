package com.example.lms.api

//class ApiService {
//}

import com.example.lms.model.AuthResponse
import com.example.lms.model.Course
import com.example.lms.model.CourseResponse
import com.example.lms.model.CoursesResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @POST("auth/register")
    fun registerUser(@Body body: Map<String, String>): Call<AuthResponse>

    @POST("auth/login")
    fun loginUser(@Body body: Map<String, String>): Call<AuthResponse>

    @POST("instrutor/course/add")
    fun addCourse(@Body course: Course): Call<Course>

    @GET("instrutor/course/get")
    fun getAllCourses(): retrofit2.Call<CoursesResponse>

    @GET("instrutor/course/get/details/{id}")
//    fun getCourseDetails(@Path("id") id: String): Call<Course>
    fun getCourseDetails(@Path("id") courseId: String): Call<CourseResponse>
}


