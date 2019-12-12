package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ursafecheck.API.APIs
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        //Code for going to back to log in page
        goBack_text.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //The procedure for sending a new OTP to the user's email id
        forgotPassword_btn.setOnClickListener{
            //Creating JSON object
            val jsonObject = JsonObject()

            //Fetching email id from text box
            val Email = forgot_email.text.toString().trim()

            //Adding all the values to the JSON object
            jsonObject.addProperty("Email",Email)
            APIs
                .service
                .forgot(jsonObject)
                .enqueue(object : Callback<ResponseBody> {
                    //code if the RESTful API sends failure response
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        println("---TTTT :: POST Throwable EXCEPTION:: " + t.message)
                        Toast.makeText(applicationContext, "Invalid Username or password", Toast.LENGTH_SHORT).show()
                    }

                    //If RESTful API responds a successful response
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val msg = response.body()?.string().toString().trim()
                            println("---TTTT :: POST msg from server :: " + msg)
                            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                })

            //Going back to the log in page
            val intent = Intent(this, MainActivity::class.java )
            startActivity(intent)

            Toast.makeText(applicationContext,"One Time password has been sent to your uregina.ca mail Id", Toast.LENGTH_LONG).show()
        }

    }
}
