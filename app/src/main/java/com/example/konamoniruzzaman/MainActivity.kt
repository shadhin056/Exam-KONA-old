package com.example.konamoniruzzaman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonAction()
    }
    private fun buttonAction() {
        btnRegCustomer.setOnClickListener {
            val intent = Intent(this@MainActivity, RegistrationActivity::class.java)
            startActivity(intent);
        }

        btnViewCustomer.setOnClickListener {
            val intent = Intent(this@MainActivity, ViewCustomer::class.java)
            startActivity(intent);
        }
    }
}