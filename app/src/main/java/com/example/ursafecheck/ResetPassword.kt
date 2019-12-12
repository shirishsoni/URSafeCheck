package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ursafecheck.API.APIs
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_reset_password.*
import okhttp3.Callback
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

//For changing the password

class ResetPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        //Creating JSON object
        val jsonObject = JsonObject()
        val Username = intent.getStringExtra("Username")

        //Button click code for submit
        reset_btn.setOnClickListener(){
            //Fetching the values from text input boxes
            val Pwd = new_pwd.text.toString().trim()
            val ConfirmPwd = confirm_pwd.text.toString().trim()

            //Making sure that both entries of password are matching
            if (Pwd == ConfirmPwd){
                //Adding values to the JSON object
                jsonObject.addProperty("Email", Username)
                jsonObject.addProperty("Pwd", Pwd)
                //Configuring the API call
                APIs
                    .service
                    .resetPwd(jsonObject)
                    .enqueue(object : retrofit2.Callback<ResponseBody> {
                        //On API call failure
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            println("---TTTT :: POST Throwable EXCEPTION:: " + t.message)
                        }

                        //On successful password reset
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                val msg = response.body()?.string()
                                println("---TTTT :: POST msg from server :: " + msg)
                                Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()

                            }
                        }
                    })
                //Going back to home page
                val intent = Intent(this, MainActivity::class.java )
                startActivity(intent)
            }
            else{
                //If the password input in both,password and confirm password text fields do not match
                Toast.makeText(applicationContext,"Password doesn't match", Toast.LENGTH_SHORT).show()

                //clearing out the text fields
                new_pwd.text.clear()
                confirm_pwd.text.clear()

            }
        }



    }
}
