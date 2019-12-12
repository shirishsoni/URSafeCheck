package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.example.ursafecheck.API.APIs
import com.google.gson.JsonObject

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//Log in activity code

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      login_signup.setOnClickListener(){
          val intent = Intent(this, SignupActivity::class.java)
          startActivity(intent)
      }

      login_btn.setOnClickListener{
          //do not remove following code segment.
          //code has been edited from the testing point of view, and will be removed in final version.
          // This will send user back to log in page but that is not how the actual app will work
          // The actual app will be executing the code right below this code bloack
          val intent = Intent(this, HomapageActivity::class.java)
          startActivity(intent)
          ////

          var msg = "temp"
          //Creating JSOn object
          val jsonObject = JsonObject()

          //Fetching all the values from the user interface
          val Email = login_email.text.toString().trim()
          val Pwd = login_pwd.text.toString().trim()
          val email_string = Email.substringAfter("@")

          //making sure the entered email id is a uregina.ca email id
          if (email_string == "uregina.ca"){
              //Adding values to the JSON object
              jsonObject.addProperty("Email",Email)
              jsonObject.addProperty("Pwd",Pwd)
              //Configuring the API call
              APIs
                  .service
                  .login(jsonObject)
                  .enqueue(object : Callback<ResponseBody> {
                      //On failure of API call
                      override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                          println("---TTTT :: POST Throwable EXCEPTION:: " + t.message)
                          Toast.makeText(applicationContext, "Invalid Username or password", Toast.LENGTH_SHORT).show()
                      }

                      //Evaluating the response from API and performing required action
                      override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                          if (response.isSuccessful) {
                              msg = response.body()?.string().toString().trim()
                              println("---TTTT :: POST msg from server :: " + msg)
                              Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()

                              val res1 = "{\"response\":\"1\"}"
                              val res2 = "{\"response\":\"2\"}"
                              val res3 = "{\"response\":\"3\"}"

                              if (msg == res1) // Login Successful
                              {
                                  // next1()
                              }
                              else if(msg == res2) // First time login
                              {
                                  next2(Email)
                              }
                              else if(msg == res3) // Incorrect username or password
                              {
                                  Toast.makeText(applicationContext, "Incorrect Password", Toast.LENGTH_SHORT).show()
                              }
                          }
                      }
                  })
          }
          else{
              //Incase the user enters an email id which is not a uregina.ca email id
              Toast.makeText(applicationContext,"Please enter uregina email address", Toast.LENGTH_LONG).show()
          }

      }

      //If the user clicks on forgot password text
      login_forgot_pwd.setOnClickListener{
          val intent = Intent(this, ForgotPasswordActivity::class.java)
          startActivity(intent)
      }
    }

    fun next1(){
        //Starting homepage activity
        val intent = Intent(this, HomapageActivity::class.java)
        startActivity(intent)
    }

    fun next2(Email: String){
        //Starting first login activity
        val intent = Intent(this, ResetPassword::class.java)
        intent.putExtra("Username",Email)
        startActivity(intent)
    }
}
