package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.databinding.ActivityTelecommunicationDetailsBinding
import com.example.personal_finance_management_app.databinding.ActivityUtilityDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TelecommunicationDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTelecommunicationDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelecommunicationDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("utility").child(uid)


        val telName = intent.getStringExtra("telName")
        val telAmount = intent.getStringExtra("telAmount")
        val telDate = intent.getStringExtra("telDate")
        val telId = intent.getStringExtra("telId")

        //bind values to editTexts
        binding.TelebillName.text=telName
        binding.TelebillAmount.text=telAmount
        binding.TelebillDate.text=telDate

        binding.btnUpdate.setOnClickListener {
            intent = Intent(applicationContext, TelecommunicationUpdateDetailsActivity::class.java).also {
                it.putExtra("telName",telName)
                it.putExtra("telAmount", telAmount)
                it.putExtra("telDate", telDate)
                it.putExtra("telId", telId)
                startActivity(it)
            }
        }

        binding.btnDelete.setOnClickListener {
            databaseRef.child(telId!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, TelecommunicationFetchingActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}