package com.example.ursafecheck.API

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class APIs {
    interface APIService {

        //Setting up the function call routes in the RESTful API
        //Also sending JSON object with each request

        @GET("/users/{user}")
        fun greetUser(@Path("user") user: String): Call<ResponseBody>

        //For adding a new user
        @Headers("Content-type: application/json")
        @POST("/api/add")
        fun addUser(@Body body: JsonObject): Call<ResponseBody>

        //For the login procedure
        @Headers("Content-type: application/json")
        @POST("/api/login")
        fun login(@Body body: JsonObject): Call<ResponseBody>

        //For the forget password procedure
        @Headers("Content-type: application/json")
        @POST("/api/forgot")
        fun forgot(@Body body: JsonObject): Call<ResponseBody>

        //For resetting the password
        @Headers("Content-type: application/json")
        @POST("/api/ResetPwd")
        fun resetPwd(@Body body: JsonObject): Call<ResponseBody>
    }

    // Retrofit generates the final request URL by embedding the IP
    // address of the server and the request
    companion object {
        private val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.227:5000")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

        var service = retrofit.create(APIService::class.java)
    }
}