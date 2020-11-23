package com.example.myweather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.signIn_Button

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference
    private var firebaseUserId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
//        auth = Firebase.auth
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        signIn_Button.setOnClickListener{
            registerUser()
        }
    }



    private fun registerUser() {
        LoginProgress.visibility = View.VISIBLE
        val userName: String = editRegisterName.editText!!.text.toString()
        val userEmail: String = editRegisterEmail.editText!!.text.toString()
        val userPassword: String = editRegisterPass.editText!!.text.toString()


        if( userName.isEmpty()) {
            LoginProgress.visibility = View.GONE
            Toast.makeText(this@RegisterActivity, "please write your user name",Toast.LENGTH_LONG,).show()
        }else if( userPassword.isEmpty()) {
            LoginProgress.visibility = View.GONE
            Toast.makeText(this@RegisterActivity, "please enter passworld",Toast.LENGTH_LONG,).show()
        }else if(userEmail.isEmpty()) {
            LoginProgress.visibility = View.GONE
            Toast.makeText(
                this@RegisterActivity,
                "Plsease enter your valid email address",
                Toast.LENGTH_LONG,
            ).show()
        } else {
            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this) {task->
                    LoginProgress.visibility = View.VISIBLE
                    if(task.isSuccessful){
                        firebaseUserId = auth.currentUser!!.uid
                        reference = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseUserId)

                        val userHashMap = HashMap<String, Any>()
                        userHashMap["uid"] = firebaseUserId
                        userHashMap["userName"] = userName

                        reference.updateChildren(userHashMap)
                            .addOnCompleteListener {task ->
                                if(task.isSuccessful){
                                    val intent = Intent(this, WelcomeActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    startActivity(intent)
                                    finish()
                                }

                            }

                    } else {
                        LoginProgress.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, "Error message: ${task.exception!!.message}",Toast.LENGTH_LONG,).show()
                    }

            }
        }
    }
}