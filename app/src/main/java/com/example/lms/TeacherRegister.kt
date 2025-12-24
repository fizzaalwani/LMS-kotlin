package com.example.lms


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lms.api.ApiClient
import com.example.lms.model.AuthResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_register)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {

            val body = mapOf(
                "userName" to etName.text.toString(),
                "userEmail" to etEmail.text.toString(),
                "password" to etPassword.text.toString(),
                "role" to "instructor"
            )

            ApiClient.apiService.registerUser(body)
                .enqueue(object : Callback<AuthResponse> {
                    override fun onResponse(
                        call: Call<AuthResponse>,
                        response: Response<AuthResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@TeacherRegister, "Registered successfully", Toast.LENGTH_SHORT).show()
                            finish() // go back to login
                        } else {
                            Toast.makeText(this@TeacherRegister, "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                        Toast.makeText(this@TeacherRegister, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}