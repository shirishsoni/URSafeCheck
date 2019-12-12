package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ursafecheck.API.APIs
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_signup.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //Going back to login page if user does not want to sign up
        signup_signin.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //Code for the submit button click signup page
        signup_submit.setOnClickListener(){
            //Creating the JSON object
            val jsonObj = JsonObject()

            //Fetching all the values from the text inputs
            val Fname = signup_fname.text.toString().trim()
            val Lname = signup_lname.text.toString().trim()
            val StdEmpID = signup_id.text.toString().trim()
            val email = signup_email.text.toString().trim()

            //making sure the entered email id is a uregina.ca email id
            val email_string = email.substringAfter("@")
            if (email_string == "uregina.ca") {

                //Adding values to the JSON object
                jsonObj.addProperty("StdEmpID", StdEmpID)
                jsonObj.addProperty("Email", email)
                jsonObj.addProperty("FName", Fname)
                jsonObj.addProperty("LName",Lname)

//  POST demo
                //Configuring the API call
                APIs
                    .service
                    .addUser(jsonObj)
                    .enqueue(object : Callback<ResponseBody> {
                        //On failure of API call
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            println("---TTTT :: POST Throwable EXCEPTION:: " + t.message)
                        }

                        //On successful response from the API
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                val msg = response.body()?.string()
                                println("---TTTT :: POST msg from server :: " + msg)
                                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()

                            }
                        }
                    })
                //Going back to login page
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                //If the user has not entered a uregina.ca email id
                Toast.makeText(applicationContext,"Please enter uregina email address", Toast.LENGTH_LONG).show()
            }

        }
    }
}
