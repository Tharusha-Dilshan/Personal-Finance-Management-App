package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.databinding.ActivityUtilityDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UtilityDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUtilityDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUtilityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("utility").child(uid)


        val utilityName = intent.getStringExtra("utilityName")
        val utilityAmount = intent.getStringExtra("utilityAmount")
        val utilityDate = intent.getStringExtra("utilityDate")
        val utilityId = intent.getStringExtra("utilityId")

        //bind values to editTexts
        binding.UtilitybillName.text=utilityName
        binding.UtilitybillAmount.text=utilityAmount
        binding.UtilitybillDate.text=utilityDate

        binding.btnUpdate.setOnClickListener {
            intent = Intent(applicationContext, UtilityUpdateDetailsActivity::class.java).also {
                it.putExtra("utilityName",utilityName)
                it.putExtra("utilityAmount", utilityAmount)
                it.putExtra("utilityDate", utilityDate)
                it.putExtra("utilityId", utilityId)
                startActivity(it)
            }
            finish()
        }

        binding.btnDelete.setOnClickListener {
            databaseRef.child(utilityId!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, UtilityFetchingActivity::class.java)
                    startActivity(intent)
                }
            }
            finish()
        }


    }
}