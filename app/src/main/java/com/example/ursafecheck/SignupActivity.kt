package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlin.math.sign

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signIn_text.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        signup_btn.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(applicationContext,"One Time password has been sent to your uregina.ca mail id",Toast.LENGTH_LONG).show()
        }
    }
}
