package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.databinding.ActivityUtilityUpdateDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UtilityUpdateDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUtilityUpdateDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUtilityUpdateDetailsBinding.inflate(layoutInflater)
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

        binding.utilityBillName.setText(utilityName)
        binding.utilityBillAmount.setText(utilityAmount)
        binding.utilityBillDate.setText(utilityDate)


        binding.utilitySaveButton.setOnClickListener {
            var name = binding.utilityBillName.text.toString()
            var amount = binding.utilityBillAmount.text.toString()
            var date = binding.utilityBillDate.text.toString()


            val map = HashMap<String, Any>()

            //add data to hashMap
            map["utilityName"] = name
            map["utilityAmount"] = amount
            map["utilityDate"] = date

            //update database from hashMap
            databaseRef.child(utilityId!!).updateChildren(map).addOnCompleteListener {
                if (it.isSuccessful) {
                    intent = Intent(applicationContext, UtilityFetchingActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Details updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}