package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      signUp_text.setOnClickListener(){
          val intent = Intent(this, SignupActivity::class.java)
          startActivity(intent)
      }

      Login.setOnClickListener({
          val intent = Intent(this, HomapageActivity::class.java)
          startActivity(intent)
      })
    }
}
