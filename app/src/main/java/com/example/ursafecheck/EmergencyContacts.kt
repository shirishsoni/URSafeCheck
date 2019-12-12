package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_emergency_contacts.*
import android.net.Uri
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

// For Emergency Contacts activity

class EmergencyContacts : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contacts)

    }
    fun CardActivity(view: View) {
        //Checking which card was clicked on
        //Performing the respective actions
        if((view as CardView) == em_c1)
        {
            // launching phone app with 911 number
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:911")
            startActivity(intent)

        }
        else if((view as CardView) == em_c2)
        {
            // launching phone app with advisor's number
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:306-585-4769")
            startActivity(intent)
        }
        else
        {
            // launching phone app with campus security's number
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:306-585-4999")
            startActivity(intent)
        }
    }
}
