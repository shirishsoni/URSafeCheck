package com.example.ursafecheck

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import kotlinx.android.synthetic.main.activity_emergency_contacts.*

class EmergencyContacts : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contacts)

    }
    fun CardActivity(view: View) {
        if((view as CardView) == em_c1)
        {
            // launching homepage
            val intent = Intent(this, HomapageActivity::class.java)
            startActivity(intent)

        }
        else if((view as CardView) == em_c2)
        {
            // launching homepage
            val intent = Intent(this, HomapageActivity::class.java)
            startActivity(intent)

        }
        else
        {
            // launching homepage
            val intent = Intent(this, HomapageActivity::class.java)
            startActivity(intent)
        }
    }
}
