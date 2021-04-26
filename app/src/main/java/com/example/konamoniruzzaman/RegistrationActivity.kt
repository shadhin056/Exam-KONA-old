package com.example.konamoniruzzaman

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*


class RegistrationActivity : AppCompatActivity() {
    internal lateinit var picker: DatePickerDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        spinnerLoad()
        spinnerOnChange()
        buttonAction()
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
                }else if (position == 1){
                    spPostOffice.setSelection(1)
                    spThane.setSelection(1)
                    spDistrict.setSelection(1)
                }else if (position == 2){
                    spPostOffice.setSelection(2)
                    spThane.setSelection(2)
                    spDistrict.setSelection(2)
                }else if (position == 3){
                    spPostOffice.setSelection(3)
                    spThane.setSelection(3)
                    spDistrict.setSelection(3)
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {

            }
        })
    }

    private fun buttonAction() {
        ibcalender_btn.setOnClickListener{
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
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
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