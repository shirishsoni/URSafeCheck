package com.example.ursafecheck

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.ursafecheck.API.APIs
import com.google.android.material.button.MaterialButton
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_incident_reporting.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class IncidentReportingActivity : AppCompatActivity(), View.OnClickListener {


    // Private variables for the Date and time Picker
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
        // Launching Activity with Transition
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)
        setContentView(R.layout.activity_incident_reporting)

        // spinner for Employment Category
        val emplyoee_types = arrayOf("Student", "Faculty", "Employee", "Visitor", "Contractor")

        val empSpinner: Spinner = findViewById(R.id.ir_employment_category_spinner)
        if (empSpinner != null) {

            // Adapter to fetch and manage spinner operations
            val arrayAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, emplyoee_types)
            empSpinner.adapter = arrayAdapter
        }

        // spinner for Medical Treatment received
        val medical_treatment = arrayOf("Family Physician", "Hospital", "Other", "No")
        val medicalSpinner: Spinner = findViewById(R.id.ir_medical_treatment_spinner)
        if (medicalSpinner != null) {
            //Adapter for to fetch and manage Medical Treatment Received
            val arrayAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, medical_treatment)
            medicalSpinner.adapter = arrayAdapter

        }

        // date and time picker and its view binders
        btnDatePicker = findViewById(R.id.ir_btn_date)
        btnTimePicker = findViewById(R.id.ir_btn_time)
        txtDate = findViewById(R.id.ir_date_field)
        txtTime= findViewById(R.id.ir_time_field)

        // Launching Date and Time Picker
        btnDatePicker.setOnClickListener(this)
        btnTimePicker.setOnClickListener(this)


        ir_submit_btn.setOnClickListener(){
            val jsonObj = JsonObject()
            //Fetching all the values from the form
            val Fname = ir_fname.text.toString().trim()
            val address = ir_address.text.toString().trim()
            val city = ir_city.text.toString().trim()
            val postal = ir_postal_code.text.toString().trim()
            val work_phone = ir_work_phone.text.toString().trim()
            val personal_phone = ir_personal_phone.text.toString().trim()
            val id = ir_id.text.toString().trim()
            val occupation = ir_occupation.text.toString().trim()
            val dept = ir_dept.text.toString().trim()
            val date = ir_date_field.text.toString().trim()
            val time = ir_time_field.text.toString().trim()
            val building = ir_building_loc.text.toString().trim()
            val room = ir_room_no.text.toString().trim()
            val incident = ir_incident_occured.text.toString().trim()
            val details = ir_detail_injury.text.toString().trim()
            val initials = ir_initials.text.toString().trim()
            val employment = empSpinner.selectedItem.toString().trim()
            val treatment = medicalSpinner.selectedItem.toString().trim()


            jsonObj.addProperty("Fname", Fname)
            jsonObj.addProperty("address", address)
            jsonObj.addProperty("city", city)
            jsonObj.addProperty("postal",postal)
            jsonObj.addProperty("workPhone",work_phone)
            jsonObj.addProperty("personalPhone",personal_phone)
            jsonObj.addProperty("id",id)
            jsonObj.addProperty("occupation",occupation)
            jsonObj.addProperty("dept",dept)
            jsonObj.addProperty("date",date)
            jsonObj.addProperty("time",time)
            jsonObj.addProperty("building",building)
            jsonObj.addProperty("room",room)
            jsonObj.addProperty("incident",incident)
            jsonObj.addProperty("details",details)
            jsonObj.addProperty("initials",initials)
            jsonObj.addProperty("employment",employment)
            jsonObj.addProperty("treatment",treatment)

            APIs
                .service
                .ifFormSubmit(jsonObj)
                .enqueue(object : Callback<ResponseBody>{
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
        }



    }

    // Handlers for Data and time Picker
    override fun onClick(view: View) {

        //code for time picker handler
        if(view === btnTimePicker){

            val c = Calendar.getInstance()
            mHour = c.get(Calendar.HOUR_OF_DAY)
            mMinute = c.get(Calendar.MINUTE)

            // Dialog for Time Picker
            val timePickerDialog = TimePickerDialog(this,
                //
                TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    txtTime?.setText("$hourOfDay:$minute")
                }, mHour, mMinute, false
            )
            // enabling the view of time Picker
            timePickerDialog.show()
        }

        // code for time picker handler
        if(view === btnDatePicker) {

            val c = Calendar.getInstance()
            mYear = c.get(Calendar.YEAR)
            mMonth = c.get(Calendar.MONTH)
            mDay = c.get(Calendar.DAY_OF_MONTH)

            // Dialog for the Data Picker
            val datePickerDialog = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    txtDate?.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                }, mYear, mMonth, mDay
            )
            //enabling the view of date Picker
            datePickerDialog.show()
        }

    }
}
