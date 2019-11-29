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

class ResetPassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)


        val jsonObject = JsonObject()
        val Username = intent.getStringExtra("Username")

        reset_btn.setOnClickListener(){
            val Pwd = new_pwd.text.toString().trim()
            val ConfirmPwd = confirm_pwd.text.toString().trim()

            if (Pwd == ConfirmPwd){
                jsonObject.addProperty("Email", Username)
                jsonObject.addProperty("Pwd", Pwd)
                APIs
                    .service
                    .resetPwd(jsonObject)
                    .enqueue(object : retrofit2.Callback<ResponseBody> {
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
                val intent = Intent(this, MainActivity::class.java )
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext,"Password doesn't match", Toast.LENGTH_SHORT).show()
                new_pwd.text.clear()
                confirm_pwd.text.clear()

            }
        }



    }
}
