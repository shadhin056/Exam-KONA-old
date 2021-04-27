package com.example.konamoniruzzaman

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import cn.pedant.SweetAlert.SweetAlertDialog
import com.shadhin.android_jetpack.view.model.CutomerModel
import com.shadhin.android_jetpack.view.view_model.CustomerViewModel
import kotlinx.android.synthetic.main.activity_registration.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern


class RegistrationActivity : AppCompatActivity() {
    private lateinit var pDialog: SweetAlertDialog
    internal lateinit var picker: DatePickerDialog
    private lateinit var viewModel: CustomerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(CustomerViewModel::class.java)
        pDialog = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
        pDialog.titleText = "Loading"
        pDialog.setCancelable(false)

        //postal code , post office , thana , district data set
        spinnerLoad()

        //change value after postal code change
        spinnerOnChange()

        buttonAction()

        //response when data inserted successfully
        observeViewModel()
    }
    private fun spinnerOnChange() {
        spPostalCode.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0) {
                    spPostOffice.setSelection(0)
                    spThane.setSelection(0)
                    spDistrict.setSelection(0)
                } else if (position == 1) {
                    spPostOffice.setSelection(1)
                    spThane.setSelection(1)
                    spDistrict.setSelection(1)
                } else if (position == 2) {
                    spPostOffice.setSelection(2)
                    spThane.setSelection(2)
                    spDistrict.setSelection(2)
                } else if (position == 3) {
                    spPostOffice.setSelection(3)
                    spThane.setSelection(3)
                    spDistrict.setSelection(3)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        })
    }
    fun ageCheck(): Boolean {

        // check if the age is >= 1
        try {
            val calendarBirthday = Calendar.getInstance()
            val calendarToday = Calendar.getInstance()
            calendarBirthday.time =
                SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(edtBirthday.text.toString())
            val yearOfToday = calendarToday[Calendar.YEAR]
            val yearOfBirthday = calendarBirthday[Calendar.YEAR]
            if (yearOfToday - yearOfBirthday >= 18) {
                return false
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return true
    }
    private fun buttonAction() {
        val anyNumberCheck = ".*\\d.*"


        btnSubmit.setOnClickListener {
            if (edtCustomerName.text.toString().length < 2) {
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Warning")
                    .setContentText("More than 2 character needed for Customer Name")
                    .show()
            } else if (Pattern.matches(anyNumberCheck, edtCustomerName.text.toString())) {
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Warning")
                    .setContentText("Number Not Allow in Customer Name")
                    .show()
            }  else if (ageCheck()) {
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Warning")
                    .setContentText("Age Less than 18 years")
                    .show()
            } else if (edtCustomerPhone.text.toString().length != 11) {
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Warning")
                    .setContentText("Number Can't more or less than 11 Digit")
                    .show()
            }else{
                pDialog.show()

                //call for data insert for customer
                submit()
            }


        }


        ibcalender_btn.setOnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr.get(Calendar.DAY_OF_MONTH)
            val month = cldr.get(Calendar.MONTH)
            val year = cldr.get(Calendar.YEAR)
            // date picker dialog
            picker = DatePickerDialog(
                this@RegistrationActivity,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    edtBirthday!!.setText(
                        dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year
                    )
                }, year, month, day
            )
            picker.show()


        }

    }
    private fun submit() {

        //data will insert after 1 second
        Handler(Looper.getMainLooper()).postDelayed({
            pDialog.dismiss()
            cutomerInfoAdd()
        }, 1000)
    }

    private fun cutomerInfoAdd() {
        val customer = CutomerModel(edtCustomerName.text.toString(), edtBirthday.text.toString(),
            edtCustomerPhone.text.toString(),
            spPostalCode.getSelectedItem().toString(),
            spPostOffice.getSelectedItem().toString(),
            spThane.getSelectedItem().toString(),
            spDistrict.getSelectedItem().toString())
        viewModel.storeCutomerLocally(customer)
    }

    private fun spinnerLoad() {

        //postal code
        val adapterForPostalCode = ArrayAdapter.createFromResource(
            this@RegistrationActivity,
            R.array.postalCode, android.R.layout.simple_spinner_item
        )
        adapterForPostalCode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPostalCode.setAdapter(adapterForPostalCode)


        //post office
        val adapterPostOffice = ArrayAdapter.createFromResource(
            this@RegistrationActivity,
            R.array.postOffice, android.R.layout.simple_spinner_item
        )
        adapterPostOffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPostOffice.setAdapter(adapterPostOffice)


        //thana
        val adapterForThana = ArrayAdapter.createFromResource(
            this@RegistrationActivity,
            R.array.thana, android.R.layout.simple_spinner_item
        )
        adapterForThana.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spThane.setAdapter(adapterForThana)

        //district
        val adapterForDistrict = ArrayAdapter.createFromResource(
            this@RegistrationActivity,
            R.array.district, android.R.layout.simple_spinner_item
        )
        adapterForDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spDistrict.setAdapter(adapterForDistrict)
    }
    private fun observeViewModel() {

        viewModel.customer.observe(this, androidx.lifecycle.Observer {
            it?.let {
                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Success!")
                    .setContentText(it)
                    .setConfirmText("Check Customer List")
                    .setConfirmClickListener { sDialog ->
                        sDialog.dismissWithAnimation()
                        var intent: Intent? = Intent(this, ViewCustomer::class.java)
                        startActivity(intent)
                    }
                    .show()

            }
        })



    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent: Intent = Intent(
                    this,
                    MainActivity::class.java
                )
                startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}

