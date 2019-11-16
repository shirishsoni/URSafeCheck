package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_login.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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
