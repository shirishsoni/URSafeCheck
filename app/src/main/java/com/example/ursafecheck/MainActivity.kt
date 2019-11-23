package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.*
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.StringRequest
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      signUp_text.setOnClickListener(){
          val intent = Intent(this, SignupActivity::class.java)
          startActivity(intent)
      }

      Login.setOnClickListener{
//          // Instantiate the RequestQueue.
          val queue = Volley.newRequestQueue(this)
          val url = "https://192.168.43.227:5000/users/"

 //Request a string response from the provided URL.
          val stringRequest = StringRequest(url,
              Response.Listener<String> {
                  // Display the first 500 characters of the response string.
                  Toast.makeText(applicationContext,"Hello",Toast.LENGTH_LONG).show()
                  //textView.text = "Response is: ${response.substring(0, 500)}"
              },
              Response.ErrorListener {Toast.makeText(applicationContext,"It didnt work",Toast.LENGTH_LONG).show() })

// Add the request to the RequestQueue.
          queue.add(stringRequest)
//          val url = "http://142.3.71.53:5000/user/200392271"
//
//          val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url,null,
//              Response.Listener { response ->
//                  val text = "Response: %s".format(response.toString())
//                  Toast.makeText(applicationContext,"One Time password has been sent to your uregina.ca mail id"+text,Toast.LENGTH_LONG).show()
//              },
//              Response.ErrorListener { error ->
//                  // TODO: Handle error
//              }
//          )
//          Toast.makeText(applicationContext,"One Time password has been sent to your uregina.ca mail id"+jsonObjectRequest,Toast.LENGTH_LONG).show()
          Toast.makeText(applicationContext,"Different"+stringRequest,Toast.LENGTH_LONG).show()
          val intent = Intent(this, HomapageActivity::class.java)
          startActivity(intent)
      }
      forgotPassword_text.setOnClickListener{
          val intent = Intent(this, forgotPasswordActivity::class.java)
          startActivity(intent)
      }
    }
}
