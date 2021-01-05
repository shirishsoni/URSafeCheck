package com.example.ursafecheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_hazard.*
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
            emptyStringArray += hazard
            emptyStringArray += prob
            emptyStringArray += freq
            emptyStringArray += severity
            emptyStringArray += total.toString()
            emptyStringArray += details
            emptyStringArray += controls

            println(emptyStringArray.contentToString())

            ra_hazards.text?.clear()
            ra_probability.text?.clear()
            ra_frequency.text?.clear()
            ra_severity.text?.clear()
            ra_details.text?.clear()
            ra_controls.text?.clear()
        }
    }
}