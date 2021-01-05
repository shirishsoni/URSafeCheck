package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ursafecheck.API.APIs
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_hazard.*
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HazardActivity : AppCompatActivity() {
    companion object {
        var emptyStringArray = arrayOf<String>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hazard)

        ra_add_new.setOnClickListener(){

            val hazard = ra_hazards.text.toString().trim()
            val prob = ra_probability.text.toString().trim()
            val freq = ra_frequency.text.toString().trim()
            val severity = ra_severity.text.toString().trim()
            val details = ra_details.text.toString().trim()
            val controls = ra_controls.text.toString().trim()
            val total = ra_probability.text.toString().toInt() + ra_frequency.text.toString().toInt() + ra_severity.text.toString().toInt()
            emptyStringArray += "Hazard: $hazard"
            emptyStringArray += "Probability: $prob"
            emptyStringArray += "Frequency: $freq"
            emptyStringArray += "Severity: $severity"
            emptyStringArray += "Total: ${total.toString()}"
            emptyStringArray += "Details: $details"
            emptyStringArray += "Controls: $controls"

            println(emptyStringArray.contentToString())

            ra_hazards.text?.clear()
            ra_probability.text?.clear()
            ra_frequency.text?.clear()
            ra_severity.text?.clear()
            ra_details.text?.clear()
            ra_controls.text?.clear()
        }

        ra_submit.setOnClickListener(){

            val hazard = ra_hazards.text.toString().trim()
            val prob = ra_probability.text.toString().trim()
            val freq = ra_frequency.text.toString().trim()
            val severity = ra_severity.text.toString().trim()
            val details = ra_details.text.toString().trim()
            val controls = ra_controls.text.toString().trim()
            val total = ra_probability.text.toString().toInt() + ra_frequency.text.toString().toInt() + ra_severity.text.toString().toInt()
            emptyStringArray += "Hazard: $hazard"
            emptyStringArray += "Probability: $prob"
            emptyStringArray += "Frequency: $freq"
            emptyStringArray += "Severity: $severity"
            emptyStringArray += "Total: ${total.toString()}"
            emptyStringArray += "Details: $details"
            emptyStringArray += "Controls: $controls"

            println(emptyStringArray.contentToString())

            ra_hazards.text?.clear()
            ra_probability.text?.clear()
            ra_frequency.text?.clear()
            ra_severity.text?.clear()
            ra_details.text?.clear()
            ra_controls.text?.clear()

            val jsonObj = JsonObject()
            val hazardDetails = emptyStringArray.contentToString()
            Toast.makeText(applicationContext, hazardDetails, Toast.LENGTH_LONG).show()
            jsonObj.addProperty("hazards",hazardDetails)

            APIs
                .service
                .raForm2Submit(jsonObj)
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
            val intent = Intent(this, HomapageActivity::class.java)
            startActivity(intent)
        }
    }
}