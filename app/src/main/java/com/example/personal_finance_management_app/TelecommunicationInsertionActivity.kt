package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.DataClasses.TelecommunicationModel
import com.example.personal_finance_management_app.databinding.ActivityTelecommunicationInsertionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TelecommunicationInsertionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTelecommunicationInsertionBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelecommunicationInsertionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("telecommunication").child(uid)

        binding.teleSaveButton.setOnClickListener{

            var teleBillName = binding.teleBillName.text.toString()
            var teleBillAmount = binding.teleBillAmount.text.toString()
            var teleBillDate = binding.teleBillDate.text.toString()


            if(teleBillName.isEmpty() || teleBillAmount.isEmpty() || teleBillDate.isEmpty()) {

                if (teleBillName.isEmpty()) {
                    binding.teleBillName.error = "Enter Bill name"
                }
                if (teleBillAmount.isEmpty()) {
                    binding.teleBillAmount.error = "Enter Bill Amount"
                }
                if (teleBillDate.isEmpty()) {
                    binding.teleBillDate.error = "Enter the Billing Date"
                }
            }else {
                //Id for new record
                var id = databaseRef.push().key!!
                //create a health object
                val tele = TelecommunicationModel( teleBillName,teleBillAmount,teleBillDate)
                databaseRef.child(id).setValue(tele).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Your Data Added successfully", Toast.LENGTH_SHORT).show()

                        binding.teleBillName.text.clear()
                        binding.teleBillAmount.text.clear()
                        binding.teleBillDate.text.clear()

                        intent = Intent(applicationContext, TelecommunicationFetchingActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}