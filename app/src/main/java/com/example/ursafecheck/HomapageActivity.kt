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

    // followings are the function call to launch the activites from the home page of this application
    fun CardActivity(view: View) {

        if((view as CardView) == c1)
        {
            // launching Search SDS Activity
            val intent = Intent(this, SearchSDSActivity::class.java)
            startActivity(intent)

        }
        else if((view as CardView) == c2)
        {
            // Launching Incident Report Reporting Activity
            val intent = Intent(this, IncidentReportingActivity::class.java)
            startActivity(intent)

        }
        else if((view as CardView) == c3)
        {
            // Launching Risk Assessment Activity
            val intent = Intent(this, RiskAssessmentActivity::class.java)
            startActivity(intent)

        }
        else if((view as CardView) == c4)
        {
            // Launching Others Activity
            val intent = Intent(this, OthersActivity::class.java)
            startActivity(intent)

        }
        else if((view as CardView) == c5)
        {
            // Launching User Mannual Activity
            val intent = Intent(this, UserMannualActivity::class.java)
            startActivity(intent)

        }
        else
        {
            // Launching Emergency Contacts Activity
            val intent = Intent(this, EmergencyContacts::class.java)
            startActivity(intent)
        }
    }


}

