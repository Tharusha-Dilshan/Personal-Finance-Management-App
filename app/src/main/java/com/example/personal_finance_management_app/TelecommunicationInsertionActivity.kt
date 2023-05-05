package com.example.personal_finance_management_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.personal_finance_management_app.DataClasses.TelecommunicationModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TelecommunicationInsertionActivity : AppCompatActivity() {

    //Initialize views
    private lateinit var telBillName: EditText
    private lateinit var telBillAmount: EditText
    private lateinit var telBillDate: EditText
    private lateinit var telSaveButton: Button

    //database reference
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_telecommunication_insertion)

        telBillName = findViewById(R.id.telBillName)
        telBillAmount = findViewById(R.id.telBillAmount)
        telBillDate = findViewById(R.id.telBillDate)

        dbRef = FirebaseDatabase.getInstance().getReference("Telecommunication")

        telSaveButton.setOnClickListener {
            saveTelecommunicationData()
        }
    }

    private fun saveTelecommunicationData() {

        //getting values
        val telBillName = telBillName.text.toString()
        val telBillAmount = telBillAmount.text.toString()
        val telBillDate = telBillDate.text.toString()

        //validations

        //create unique ID for persons - to avoid overriding data
        val telId = dbRef.push().key!!

        val telecommunication =
            TelecommunicationModel(telId, telBillName, telBillAmount, telBillDate)

        dbRef.child(telId).setValue(telecommunication)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener{ err->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}