package com.example.ursafecheck

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.widget.ButtonBarLayout
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_incident_reporting.*
import java.util.*
import javax.microedition.khronos.egl.EGLDisplay

class IncidentReportingActivity : AppCompatActivity(), View.OnClickListener {


    private var mYear: Int = 0
    private var mMonth: Int = 0
    private var mDay: Int = 0
    private var mHour: Int = 0
    private var mMinute: Int = 0

    // lazy initialization of Date and Time Picker
    lateinit var btnDatePicker: MaterialButton
    lateinit var btnTimePicker: MaterialButton
    lateinit var txtDate: EditText
    lateinit var txtTime:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        setContentView(R.layout.activity_incident_reporting)

        // spinner for Employment Category
        val emplyoee_types = arrayOf("Student", "Faculty", "Employee", "Visitor", "Contractor")
        val empSpinner: Spinner = findViewById(R.id.employement_category_spinner)
        if (empSpinner != null) {
            val arrayAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, emplyoee_types)
            empSpinner.adapter = arrayAdapter

        }

        // spinner for Medical Treatment received
        val medical_treatment = arrayOf("Family Physician", "Hospital", "Other", "No")
        val medicalSpinner: Spinner = findViewById(R.id.medical_treatment_spinner)
        if (medicalSpinner != null) {
            val arrayAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, medical_treatment)
            medicalSpinner.adapter = arrayAdapter

        }

        // date and time picker and its view binders
        btnDatePicker = findViewById(R.id.inc_btn_date)
        btnTimePicker = findViewById(R.id.inc_btn_time)
        txtDate = findViewById(R.id.inc_date_field)
        txtTime= findViewById(R.id.inc_time_field)

        btnDatePicker.setOnClickListener(this)
        btnTimePicker.setOnClickListener(this)

    }

    override fun onClick(view: View) {

        //code for time picker handler
        if(view === btnTimePicker){

            val c = Calendar.getInstance()
            mHour = c.get(Calendar.HOUR_OF_DAY)
            mMinute = c.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this,
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    txtTime?.setText("$hourOfDay:$minute")
                }, mHour, mMinute, false
            )
            timePickerDialog.show()
        }

        // code for time picker handler
        if(view === btnDatePicker) {

            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    txtDate?.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

    }
}
