package com.example.personal_finance_management_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personal_finance_management_app.DataClasses.UtilityModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UtilityInsertionActivity : AppCompatActivity() {

    //Initialize views
    private lateinit var utilityBillName : EditText
    private lateinit var utilityBillAmount : EditText
    private lateinit var utilityBillDate : EditText
    private lateinit var utilitySaveButton : Button

    //database reference
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(/* layoutResID = */ R.layout.activity_utility_insertion)

        utilityBillName = findViewById(R.id.utilityBillName)
        utilityBillAmount = findViewById(R.id.utilityBillAmount)
        utilityBillDate = findViewById(R.id.utilityBillDate)
        utilitySaveButton = findViewById(R.id.utilitySaveButton)

        dbRef = FirebaseDatabase.getInstance().getReference("Utility")

        utilitySaveButton.setOnClickListener {
            saveUtilityData()
        }
    }

    private fun saveUtilityData() {

        //getting values
        val utilityBillName = utilityBillName.text.toString()
        val utilityBillAmount = utilityBillAmount.text.toString()
        val utilityBillDate = utilityBillDate.text.toString()

        //validations
        if(utilityBillName.isEmpty()){
            utilityBillName.error = "Please enter name"
        }
        if(utilityBillAmount.isEmpty()){
            utilityBillAmount.error = "Please enter Age"
            return
        }
        if(utilityBillDate.isEmpty()){
            utilityBillDate.error = "Please enter Salary"
            return
        }

        //create unique ID for persons - to avoid overriding data
        val utilityId = dbRef.push().key!!

        val utility = UtilityModel(utilityId,utilityBillName,utilityBillAmount,utilityBillDate)

        dbRef.child(utilityId).setValue(utility)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()

                utilityBillName.text.clear()
                utilityBillAmount.text.clear()
                utilityBillDate.text.clear()

            }.addOnFailureListener{ err->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}

