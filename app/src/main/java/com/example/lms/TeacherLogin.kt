package com.example.lms

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lms.model.AuthResponse
import com.example.lms.api.ApiClient
import com.example.lms.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherLogin: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_login)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        val tvSignup = findViewById<TextView>(R.id.tvSignup)
        tvSignup.setOnClickListener {
            val intent = Intent(this@TeacherLogin, TeacherRegister::class.java)
            startActivity(intent)
        }


        val session = SessionManager(this)

        btnLogin.setOnClickListener {

            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty()) {
                etEmail.error = "Email is required"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "Password is required"
                return@setOnClickListener
            }

            val body = mapOf(
                "userEmail" to email,
                "password" to password
            )

            login(body)

        }
    }

    private fun login(body: Map<String, String>) {

        val session = SessionManager(this)

        ApiClient.apiService.loginUser(body)
            .enqueue(object : Callback<AuthResponse> {

                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    if (response.isSuccessful && response.body() != null) {

                        val token = response.body()!!.data?.accessToken

                        if (token.isNullOrEmpty()) {
                            Toast.makeText(
                                this@TeacherLogin,
                                "Token missing in response",
                                Toast.LENGTH_LONG
                            ).show()
                            return
                        }

                        session.saveToken(token)

                        startActivity(
                            Intent(this@TeacherLogin, TeacherDashboard::class.java)
                        )
//                        finish()

                    } else {
                        // READ BACKEND ERROR MESSAGE
                        val errorMsg = response.errorBody()?.string()
                        Toast.makeText(
                            this@TeacherLogin,
                            errorMsg ?: "Login failed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(
                        this@TeacherLogin,
                        "Network error: ${t.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

}