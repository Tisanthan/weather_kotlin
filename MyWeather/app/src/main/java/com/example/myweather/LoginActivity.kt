package com.example.myweather

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        signIn_Button.setOnClickListener {
            loginUser()
        }
    }



    private fun loginUser() {
        val userEmail = editSignInEmail.editText!!.text.toString().trim()
        val userPassword = editSignInPass.editText!!.text.toString().trim()
        LoginProgress.visibility = View.VISIBLE
        if (userEmail.isEmpty()) {
            LoginProgress.visibility = View.GONE
            Toast.makeText(this@LoginActivity, "Please enter your valid email address", Toast.LENGTH_LONG,).show()
        } else if (userPassword.isEmpty()) {
            LoginProgress.visibility = View.GONE
            Toast.makeText(this@LoginActivity, "Please enter your Correct password",Toast.LENGTH_LONG,).show()
        } else {
            auth.signInWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, WelcomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        finish()
                    }else {
                        LoginProgress.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Error message: ${task.exception!!.message}",Toast.LENGTH_LONG,).show()
                    }
                }
        }
    }
}