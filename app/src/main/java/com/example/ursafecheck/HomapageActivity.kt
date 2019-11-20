package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.Toast
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_homapage.*
import java.lang.Math.log


class HomapageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homapage)
    }

    fun CardActivity(view: View) {

        if((view as CardView) == c1)
        {
            val intent = Intent(this, SearchSDSActivity::class.java)
            startActivity(intent)

        }
        else if((view as CardView) == c2)
        {
            val intent = Intent(this, IncidentReportingActivity::class.java)
            startActivity(intent)

        }
        else if((view as CardView) == c3)
        {
            val intent = Intent(this, RiskAssessmentActivity::class.java)
            startActivity(intent)

        }
        else if((view as CardView) == c4)
        {
            val intent = Intent(this, OthersActivity::class.java)
            startActivity(intent)

        }
        else if((view as CardView) == c5)
        {
            val intent = Intent(this, UserMannualActivity::class.java)
            startActivity(intent)

        }
        else
        {
            val intent = Intent(this, EmergencyContacts::class.java)
            startActivity(intent)
        }
    }


}

