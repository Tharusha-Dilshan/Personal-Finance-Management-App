package com.example.personal_finance_management_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.personal_finance_management_app.databinding.ActivityExpensesBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

private lateinit var binding: ActivityExpensesBinding

class ExpensesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpensesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

//        btnUtility = findViewById(R.id.btnUtility)
//        btnTelecommunication = findViewById(R.id.btnTelecommunication)
//        btnHealth = findViewById(R.id.btnHealth)
//        btnFood = findViewById(R.id.btnFood)
//        btnGroceries = findViewById(R.id.btnGroceries)
//        btnLoan = findViewById(R.id.btnLoan)
//        btnLeasing = findViewById(R.id.btnLeasing)
//        btnTransaction = findViewById(R.id.btnTransaction)
//        btnOther = findViewById(R.id.btnOther)

        binding.btnUtility.setOnClickListener {
            val intent = Intent(this, UtilityFetchingActivity::class.java)
            startActivity(intent)
        }

        binding.btnTelecommunication.setOnClickListener{
            val intent = Intent(this, TelecommunicationFetchingActivity::class.java)
            startActivity(intent)
        }

        binding.btnHealth.setOnClickListener{
            val intent = Intent(this, HealthFetchingActivity::class.java)
            startActivity(intent)
        }

        binding.backBtn.setOnClickListener {
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
            finish()
        }
    }
}