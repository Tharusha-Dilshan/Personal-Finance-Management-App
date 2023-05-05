package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TelecommunicationFetchingActivity : AppCompatActivity() {

    private lateinit var addTel: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telecommunication_fetching)

        addTel = findViewById(R.id.addTel)

        addTel.setOnClickListener{
            val intent = Intent(this, TelecommunicationInsertionActivity::class.java)
            startActivity(intent)
        }
    }
}