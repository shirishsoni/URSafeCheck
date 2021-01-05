package com.example.ursafecheck

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ursafecheck.API.APIs
import com.google.android.material.button.MaterialButton
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_risk_assessment.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.content.Intent

//For future development
class RiskAssessmentActivity : AppCompatActivity(), View.OnClickListener {

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

    lateinit var rb1_value: String
    lateinit var rb2_value: String
    lateinit var rb3_value: String
    lateinit var rb4_value: String
    lateinit var rb5_value: String
    lateinit var rb6_value: String
    lateinit var rb7_value: String
    lateinit var rb8_value: String
    lateinit var rb9_value: String
    lateinit var rb10_value: String
    lateinit var rb11_value: String
    lateinit var rb12_value: String
    lateinit var rb13_value: String
    lateinit var rb14_value: String
    lateinit var rb15_value: String
    lateinit var rb16_value: String
    lateinit var rb17_value: String
    lateinit var rb18_value: String
    lateinit var rb19_value: String
    lateinit var rb20_value: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Launching Activity with Transition
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
        setContentView(R.layout.activity_risk_assessment)

        // date and time picker and its view binders
        btnDatePicker = findViewById(R.id.ra_btn_date)
        btnTimePicker = findViewById(R.id.ra_btn_time)
        txtDate = findViewById(R.id.ra_date_field)
        txtTime = findViewById(R.id.ra_time_field)

        // Launching Date and Time Picker
        btnDatePicker.setOnClickListener(this)
        btnTimePicker.setOnClickListener(this)

        ra_cnt_btn.setOnClickListener(){
            val jsonObj = JsonObject()
            //Fetching all the values from the form
            val Ename = ra_fname.text.toString().trim()
            val sup_name = ra_sup_name.text.toString().trim()
            val job_title = ra_job_title.text.toString().trim()
            val dept = ra_dept.text.toString().trim()
            val mob_no = ra_mob_no.text.toString().trim()
            val email = ra_email.text.toString().trim()
            val sup_email = ra_sup_email.text.toString().trim()
            val sup_no = ra_sup_no.text.toString().trim()
            val emo_no = ra_emergency_no.text.toString().trim()
            val adv_name = ra_sfty_adv_name.text.toString().trim()
            val location = ra_location.text.toString().trim()
            val date = ra_date_field.text.toString().trim()
            val time = ra_time_field.text.toString().trim()
            val task = ra_risk_task.text.toString().trim()
            val comments = ra_comments.text.toString().trim()
            val rb1_ques = ra_rb1_title.toString().trim()
            val rb2_ques = ra_rb2_title.toString().trim()
            val rb3_ques = ra_rb3_title.toString().trim()
            val rb4_ques = ra_rb4_title.toString().trim()
            val rb5_ques = ra_rb5_title.toString().trim()
            val rb6_ques = ra_rb6_title.toString().trim()
            val rb7_ques = ra_rb7_title.toString().trim()
            val rb8_ques = ra_rb8_title.toString().trim()
            val rb9_ques = ra_rb9_title.toString().trim()
            val rb10_ques = ra_rb10_title.toString().trim()
            val rb11_ques = ra_rb11_title.toString().trim()
            val rb12_ques = ra_rb12_title.toString().trim()
            val rb13_ques = ra_rb13_title.toString().trim()
            val rb14_ques = ra_rb14_title.toString().trim()
            val rb15_ques = ra_rb15_title.toString().trim()
            val rb16_ques = ra_rb16_title.toString().trim()
            val rb17_ques = ra_rb17_title.toString().trim()
            val rb18_ques = ra_rb18_title.toString().trim()
            val rb19_ques = ra_rb19_title.toString().trim()
            val rb20_ques = ra_rb20_title.toString().trim()

            if(ra_rb1_yes.isChecked)
            {
                rb1_value ="Yes"
            }
            else
            {
                rb1_value ="No"
            }

            if(ra_rb2_yes.isChecked)
            {
                rb2_value ="Yes"
            }
            else
            {
                rb2_value ="No"
            }

            if(ra_rb3_yes.isChecked)
            {
                rb3_value ="Yes"
            }
            else
            {
                rb3_value ="No"
            }

            if(ra_rb4_yes.isChecked)
            {
                rb4_value ="Yes"
            }
            else
            {
                rb4_value ="No"
            }

            if(ra_rb5_yes.isChecked)
            {
                rb5_value ="Yes"
            }
            else
            {
                rb5_value ="No"
            }

            if(ra_rb6_yes.isChecked)
            {
                rb6_value ="Yes"
            }
            else
            {
                rb6_value ="No"
            }

            if(ra_rb7_yes.isChecked)
            {
                rb7_value ="Yes"
            }
            else
            {
                rb7_value ="No"
            }

            if(ra_rb8_yes.isChecked)
            {
                rb8_value ="Yes"
            }
            else
            {
                rb8_value ="No"
            }

            if(ra_rb9_yes.isChecked)
            {
                rb9_value ="Yes"
            }
            else
            {
                rb9_value ="No"
            }

            if(ra_rb10_yes.isChecked)
            {
                rb10_value ="Yes"
            }
            else
            {
                rb10_value ="No"
            }

            if(ra_rb11_yes.isChecked)
            {
                rb11_value ="Yes"
            }
            else if (ra_rb11_no.isChecked)
            {
                rb11_value ="No"
            }
            else{
                rb11_value = "N/A"
            }


            if(ra_rb12_yes.isChecked)
            {
                rb12_value ="Yes"
            }
            else
            {
                rb12_value ="No"
            }

            if(ra_rb13_yes.isChecked)
            {
                rb13_value ="Yes"
            }
            else
            {
                rb13_value ="No"
            }

            if(ra_rb14_yes.isChecked)
            {
                rb14_value ="Yes"
            }
            else
            {
                rb14_value ="No"
            }

            if(ra_rb15_yes.isChecked)
            {
                rb15_value ="Yes"
            }
            else
            {
                rb15_value ="No"
            }

            if(ra_rb16_yes.isChecked)
            {
                rb16_value ="Yes"
            }
            else
            {
                rb16_value ="No"
            }

            if(ra_rb17_yes.isChecked)
            {
                rb17_value ="Yes"
            }
            else
            {
                rb17_value ="No"
            }

            if(ra_rb18_yes.isChecked)
            {
                rb18_value ="Yes"
            }
            else
            {
                rb18_value ="No"
            }

            if(ra_rb19_yes.isChecked)
            {
                rb19_value ="Yes"
            }
            else
            {
                rb19_value ="No"
            }

            if(ra_rb20_yes.isChecked)
            {
                rb20_value ="Yes"
            }
            else
            {
                rb20_value ="No"
            }


            jsonObj.addProperty("Ename", Ename)
            jsonObj.addProperty("sup_name", sup_name)
            jsonObj.addProperty("job_title", job_title)
            jsonObj.addProperty("dept",dept)
            jsonObj.addProperty("mob_no",mob_no)
            jsonObj.addProperty("email",email)
            jsonObj.addProperty("sup_email",sup_email)
            jsonObj.addProperty("sup_no",sup_no)
            jsonObj.addProperty("emo_no",emo_no)
            jsonObj.addProperty("adv_name",adv_name)
            jsonObj.addProperty("location",location)
            jsonObj.addProperty("date",date)
            jsonObj.addProperty("time",time)
            jsonObj.addProperty("task",task)
            jsonObj.addProperty("comments",comments)
            jsonObj.addProperty("rb1_ques",rb1_ques)
            jsonObj.addProperty("rb1_value",rb1_value)
            jsonObj.addProperty("rb2_ques",rb2_ques)
            jsonObj.addProperty("rb2_value",rb2_value)
            jsonObj.addProperty("rb3_ques",rb3_ques)
            jsonObj.addProperty("rb3_value",rb3_value)
            jsonObj.addProperty("rb4_ques",rb4_ques)
            jsonObj.addProperty("rb4_value",rb4_value)
            jsonObj.addProperty("rb5_ques",rb5_ques)
            jsonObj.addProperty("rb5_value",rb5_value)
            jsonObj.addProperty("rb6_ques",rb6_ques)
            jsonObj.addProperty("rb6_value",rb6_value)
            jsonObj.addProperty("rb7_ques",rb7_ques)
            jsonObj.addProperty("rb7_value",rb7_value)
            jsonObj.addProperty("rb8_ques",rb8_ques)
            jsonObj.addProperty("rb8_value",rb8_value)
            jsonObj.addProperty("rb9_ques",rb9_ques)
            jsonObj.addProperty("rb9_value",rb9_value)
            jsonObj.addProperty("rb10_ques",rb10_ques)
            jsonObj.addProperty("rb10_value",rb10_value)
            jsonObj.addProperty("rb11_ques",rb11_ques)
            jsonObj.addProperty("rb11_value",rb11_value)
            jsonObj.addProperty("rb12_ques",rb12_ques)
            jsonObj.addProperty("rb12_value",rb12_value)
            jsonObj.addProperty("rb13_ques",rb13_ques)
            jsonObj.addProperty("rb13_value",rb13_value)
            jsonObj.addProperty("rb14_ques",rb14_ques)
            jsonObj.addProperty("rb14_value",rb14_value)
            jsonObj.addProperty("rb15_ques",rb15_ques)
            jsonObj.addProperty("rb15_value",rb15_value)
            jsonObj.addProperty("rb16_ques",rb16_ques)
            jsonObj.addProperty("rb16_value",rb16_value)
            jsonObj.addProperty("rb17_ques",rb17_ques)
            jsonObj.addProperty("rb17_value",rb17_value)
            jsonObj.addProperty("rb18_ques",rb18_ques)
            jsonObj.addProperty("rb18_value",rb18_value)
            jsonObj.addProperty("rb19_ques",rb19_ques)
            jsonObj.addProperty("rb19_value",rb19_value)
            jsonObj.addProperty("rb20_ques",rb20_ques)
            jsonObj.addProperty("rb20_value",rb20_value)


            APIs
                .service
                .raForm1Submit(jsonObj)
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

            val intent = Intent(this, HazardActivity::class.java)
            //val intent = Intent(this, HazardActivity::class.java)
            startActivity(intent)
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

    fun working_alone(view: View) {
        Toast.makeText(this, "Following the risk assessment procedure of the other form", Toast.LENGTH_SHORT).show()
    }

    fun authorised(view: View) {
        Toast.makeText(this, "Please complete appropriate training before continuing", Toast.LENGTH_SHORT).show()
    }

    fun equipment(view: View) {
        Toast.makeText(this, "Please acquire all proper equipment before continuing", Toast.LENGTH_SHORT).show()
    }

    fun notAble(view: View) {
        Toast.makeText(this, "Please contact your supervisor to make arrangements for the task to be completed", Toast.LENGTH_SHORT).show()
    }

    fun ppe(view: View) {
        Toast.makeText(this, "Please acquire the proper PPE before carrying out this task", Toast.LENGTH_SHORT).show()
    }

    fun conditions(view: View) {
        Toast.makeText(this, "Following the risk assessment procedure from other form to figure out how to possibly work around the conditions that are not ideal", Toast.LENGTH_SHORT).show()
    }

    fun comfortable(view: View) {
        Toast.makeText(this, "Follow risk assessment procedure to make conditions better to perform the task", Toast.LENGTH_SHORT).show()
    }

    fun others(view: View) {
        Toast.makeText(this, "Please notify others in the lab that an experiment is being carried out", Toast.LENGTH_SHORT).show()
    }

    fun supervisor(view: View) {
        Toast.makeText(this, "Please notify your supervisor of all experiments being performed", Toast.LENGTH_SHORT).show()
    }

    fun driving(view: View) {
        Toast.makeText(this, "Please wait for suitable driving conditions", Toast.LENGTH_SHORT).show()
    }

    fun PossibleHazards(view: View) {
        Toast.makeText(this, "Please follow risk assessment procedures", Toast.LENGTH_SHORT).show()
    }
}
