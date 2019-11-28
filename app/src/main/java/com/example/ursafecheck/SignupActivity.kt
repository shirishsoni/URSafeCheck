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

        signup_signin.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        signup_submit.setOnClickListener(){
            val jsonObj = JsonObject()
            val Fname = signup_fname.text.toString().trim()
            val Lname = signup_lname.text.toString().trim()
            val StdEmpID = signup_id.text.toString().trim()
            val email = "tempemail@uregina.ca"
            val Pwd = "123456"
            jsonObj.addProperty("StdEmpID", StdEmpID)
            jsonObj.addProperty("Email", email)
            jsonObj.addProperty("Pwd", Pwd)
            jsonObj.addProperty("FName", Fname)
            jsonObj.addProperty("LName",Lname)

//  POST demo
            APIs
                .service
                .addUser(jsonObj)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        println("---TTTT :: POST Throwable EXCEPTION:: " + t.message)
                    }

                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val msg = response.body()?.string()
                            println("---TTTT :: POST msg from server :: " + msg)
                            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()

                        }
                    }
                })
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
