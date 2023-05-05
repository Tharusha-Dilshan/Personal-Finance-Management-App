package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personal_finance_management_app.databinding.ActivityHealthFetchingBinding
import com.example.personal_finance_management_app.databinding.ActivityPortfolioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class HealthFetchingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHealthFetchingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addHealth.setOnClickListener{
            intent = Intent(applicationContext, HealthInsertionActivity::class.java)
            startActivity(intent)
        }

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
    }
}