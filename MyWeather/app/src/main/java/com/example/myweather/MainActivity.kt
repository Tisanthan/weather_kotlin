package com.example.myweather

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    public override  fun onStart() {
        super.onStart()
        makeVerify()
    }

    private fun makeVerify() {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        signIn_button.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        register_button.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

}