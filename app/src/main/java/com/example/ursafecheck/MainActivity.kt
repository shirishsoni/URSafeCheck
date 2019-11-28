package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      login_signup.setOnClickListener(){
          val intent = Intent(this, SignupActivity::class.java)
          startActivity(intent)
      }

      login_btn.setOnClickListener{
          val intent = Intent(this, HomapageActivity::class.java)
          startActivity(intent)
      }
      login_forgot_pwd.setOnClickListener{
          val intent = Intent(this, ForgotPasswordActivity::class.java)
          startActivity(intent)
      }
    }
}
