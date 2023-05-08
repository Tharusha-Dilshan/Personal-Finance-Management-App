package com.example.personal_finance_management_app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.personal_finance_management_app.databinding.ActivityHealthDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HealthDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHealthDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("health").child(uid)


        val healthName = intent.getStringExtra("healthName")
        val healthAmount = intent.getStringExtra("healthAmount")
        val healthDate = intent.getStringExtra("healthDate")
        val healthId = intent.getStringExtra("healthId")

        //bind values to editTexts
        binding.HealthbillName.text=healthName
        binding.HealthbillAmount.text=healthAmount
        binding.HealthbillDate.text=healthDate

        binding.btnUpdate.setOnClickListener {
            intent = Intent(applicationContext, HealthUpdateDetailsActivity::class.java).also {
                it.putExtra("healthName",healthName)
                it.putExtra("healthAmount", healthAmount)
                it.putExtra("healthDate", healthDate)
                it.putExtra("healthId", healthId)
                startActivity(it)
            }
        }

        binding.btnDelete.setOnClickListener {
            databaseRef.child(healthId!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, HealthFetchingActivity::class.java)
                    startActivity(intent)
                }
            }
        }


    }
}