package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_forgot_password.*

class forgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        goBack_text.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        forgotPassword_btn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java )
            startActivity(intent)

            Toast.makeText(applicationContext,"One Time password has been sent to your uregina.ca mail Id", Toast.LENGTH_LONG).show()
        }

    }
}
