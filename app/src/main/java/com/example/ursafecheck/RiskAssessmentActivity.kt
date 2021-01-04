package com.example.ursafecheck

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_risk_assessment.*
import java.util.*

//For future development
class RiskAssessmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk_assessment)

        ra_cnt_btn.setOnClickListener(){
            val intent = Intent(this, HazardActivity::class.java)
            startActivity(intent)
        }
    }
}
