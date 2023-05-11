package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.databinding.ActivityTelecommunicationUpdateDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TelecommunicationUpdateDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTelecommunicationUpdateDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelecommunicationUpdateDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("telecommunication").child(uid)


        val telName = intent.getStringExtra("telName")
        val telAmount = intent.getStringExtra("telAmount")
        val telDate = intent.getStringExtra("telDate")
        val telId = intent.getStringExtra("telId")

        binding.teleBillName.setText(telName)
        binding.teleBillAmount.setText(telAmount)
        binding.teleBillDate.setText(telDate)


        binding.teleSaveButton.setOnClickListener {
            var name = binding.teleBillName.text.toString()
            var amount = binding.teleBillAmount.text.toString()
            var date = binding.teleBillDate.text.toString()


            val map = HashMap<String, Any>()

            //add data to hashMap
            map["telName"] = name
            map["telAmount"] = amount
            map["telDate"] = date

            //update database from hashMap
            databaseRef.child(telId!!).updateChildren(map).addOnCompleteListener {
                if (it.isSuccessful) {
//                    intent = Intent(applicationContext, TelecommunicationFetchingActivity::class.java)
//                    startActivity(intent)
                    Toast.makeText(this, "Details updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
            finish()
        }
    }
}