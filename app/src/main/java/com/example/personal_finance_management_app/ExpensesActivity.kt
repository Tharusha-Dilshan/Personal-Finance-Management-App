package com.example.personal_finance_management_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ExpensesActivity : AppCompatActivity() {

    private lateinit var btnUtility: Button
    private lateinit var btnTelecommunication: Button
    private lateinit var btnHealth: Button
    private lateinit var btnFood: Button
    private lateinit var btnGroceries: Button
    private lateinit var btnLoan: Button
    private lateinit var btnLeasing: Button
    private lateinit var btnTransaction: Button
    private lateinit var btnOther: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expenses)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnUtility = findViewById(R.id.btnUtility)
        btnTelecommunication = findViewById(R.id.btnTelecommunication)
        btnHealth = findViewById(R.id.btnHealth)
        btnFood = findViewById(R.id.btnFood)
        btnGroceries = findViewById(R.id.btnGroceries)
        btnLoan = findViewById(R.id.btnLoan)
        btnLeasing = findViewById(R.id.btnLeasing)
        btnTransaction = findViewById(R.id.btnTransaction)
        btnOther = findViewById(R.id.btnOther)

//        btnInsertData.setOnClickListener {
//            val intent = Intent(this, InsertionActivity::class.java)
//            startActivity(intent)
//        }
//
        btnUtility.setOnClickListener {
            val intent = Intent(this, UtilityFetchingActivity::class.java)
            startActivity(intent)
        }

        btnTelecommunication.setOnClickListener{
            val intent = Intent(this, TelecommunicationFetchingActivity::class.java)
            startActivity(intent)
        }
    }
}