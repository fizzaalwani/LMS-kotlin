package com.example.lms.model

import com.google.gson.annotations.SerializedName

//Response wrapper for all courses
data class CoursesResponse(
    val success: Boolean,
    val data: List<Course>
)

// Response wrapper for single course
data class CourseResponse(
    val success: Boolean,
    val data: Course
)
data class Course(
    @SerializedName("_id")
    val id: String? = null,
    val instructorId: String? = null,
    val instructorName: String? = null,
    val instructorEmail: String? = null,
    val date: String? = null,
    val title: String? = null,
    val category: String? = null,
    val level: String? = null,
    val description: String? = null,
    val subtitle: String? = null,
    val objectives: String? = null,
    val language: String? = null,
    val welcomeMessage: String? = null,
    val image: String? = null,
    val price: Int? = null,
    val isPublished: Boolean? = null
)