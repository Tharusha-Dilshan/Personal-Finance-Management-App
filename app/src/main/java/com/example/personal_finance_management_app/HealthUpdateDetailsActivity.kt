package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.databinding.ActivityHealthUpdateDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HealthUpdateDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHealthUpdateDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthUpdateDetailsBinding.inflate(layoutInflater)
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

        binding.healthBillName.setText(healthName)
        binding.healthBillAmount.setText(healthAmount)
        binding.healthBillDate.setText(healthDate)


        binding.healthSaveButton.setOnClickListener {
            var name = binding.healthBillName.text.toString()
            var amount = binding.healthBillAmount.text.toString()
            var date = binding.healthBillDate.text.toString()


            val map = HashMap<String, Any>()

            //add data to hashMap
            map["healthName"] = name
            map["healthAmount"] = amount
            map["healthDate"] = date

            //update database from hashMap
            databaseRef.child(healthId!!).updateChildren(map).addOnCompleteListener {
                if (it.isSuccessful) {
                    intent = Intent(applicationContext, HealthFetchingActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Details updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}