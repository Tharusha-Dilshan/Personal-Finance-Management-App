package com.example.personal_finance_management_app

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.DataClasses.Asset
import com.example.personal_finance_management_app.DataClasses.HealthModel
import com.example.personal_finance_management_app.databinding.ActivityDashHomeBinding
import com.example.personal_finance_management_app.databinding.ActivityHealthInsertionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HealthInsertionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHealthInsertionBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthInsertionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("health").child(uid)

        binding.healthSaveButton.setOnClickListener{

            var healthBillName = binding.healthBillName.text.toString()
            var healthBillAmount = binding.healthBillAmount.text.toString()
            var healthBillDate = binding.healthBillDate.text.toString()


            if(healthBillName.isEmpty() ||healthBillAmount.isEmpty() ||healthBillDate.isEmpty()) {

                if (healthBillName.isEmpty()) {
                    binding.healthBillName.error = "Enter Bill name"
                }
                if (healthBillAmount.isEmpty()) {
                    binding.healthBillAmount.error = "Enter Bill Amount"
                }
                if (healthBillDate.isEmpty()) {
                    binding.healthBillDate.error = "Enter the Billing Date"
                }
            }else {
                //Id for new record
                var id = databaseRef.push().key!!
                //create a health object
                val health = HealthModel( healthBillName,healthBillAmount,healthBillDate)
                databaseRef.child(id).setValue(health).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Your Data Added successfully", Toast.LENGTH_SHORT).show()
                        intent = Intent(applicationContext, HealthFetchingActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}