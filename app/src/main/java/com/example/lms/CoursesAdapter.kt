package com.example.lms

//
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.lms.model.Course
//
//class CoursesAdapter(
//    private var courses: List<Course>,
//    private val onItemClick: (Course) -> Unit
//) : RecyclerView.Adapter<CoursesAdapter.CourseViewHolder>() {
//
//    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val title: TextView = itemView.findViewById(R.id.courseTitle)
//        val price: TextView = itemView.findViewById(R.id.coursePrice)
//        val category: TextView = itemView.findViewById(R.id.courseCategory)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_course, parent, false)
//        return CourseViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
//        val course = courses[position]
//        holder.title.text = course.title ?: "Untitled Course"
//        holder.price.text = "$${course.price ?: 0}"
//        holder.category.text = course.category ?: "Uncategorized"
//
//        // Handle click
//        holder.itemView.setOnClickListener {
//            onItemClick(course)
//        }
//    }
//
//    override fun getItemCount(): Int = courses.size
//
//    fun updateCourses(newCourses: List<Course>) {
//        courses = newCourses
//        notifyDataSetChanged()
//    }
//}
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.model.Course

class CoursesAdapter(
    private var courses: List<Course>,
    private val onItemClick: (Course) -> Unit
) : RecyclerView.Adapter<CoursesAdapter.CourseViewHolder>() {

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.courseTitle)
        val price: TextView = itemView.findViewById(R.id.coursePrice)
        val category: TextView = itemView.findViewById(R.id.courseCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        holder.title.text = course.title ?: "Untitled Course"
        holder.price.text = "$${course.price ?: 0}"
        holder.category.text = course.category ?: "Uncategorized"

        // Handle click
        holder.itemView.setOnClickListener {
            onItemClick(course)
        }
    }

    override fun getItemCount(): Int = courses.size

    fun updateCourses(newCourses: List<Course>) {
        courses = newCourses
        notifyDataSetChanged()
    }
}