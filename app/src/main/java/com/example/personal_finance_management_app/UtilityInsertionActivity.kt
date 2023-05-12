package com.example.personal_finance_management_app

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.DataClasses.HealthModel
import com.example.personal_finance_management_app.DataClasses.UtilityModel
import com.example.personal_finance_management_app.databinding.ActivityDashHomeBinding
import com.example.personal_finance_management_app.databinding.ActivityUtilityInsertionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UtilityInsertionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUtilityInsertionBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUtilityInsertionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("utility").child(uid)

        binding.utilitySaveButton.setOnClickListener{

            var utilityBillName = binding.utilityBillName.text.toString()
            var utilityBillAmount = binding.utilityBillAmount.text.toString()
            var utilityBillDate = binding.utilityBillDate.text.toString()


            if(utilityBillName.isEmpty() || utilityBillAmount.isEmpty() || utilityBillDate.isEmpty()) {

                if (utilityBillName.isEmpty()) {
                    binding.utilityBillName.error = "Enter Bill name"
                }
                if (utilityBillAmount.isEmpty()) {
                    binding.utilityBillAmount.error = "Enter Bill Amount"
                }
                if (utilityBillDate.isEmpty()) {
                    binding.utilityBillDate.error = "Enter the Billing Date"
                }
            }else {
                //Id for new record
                var id = databaseRef.push().key!!
                //create a utility object
                val utility = UtilityModel( utilityBillName,utilityBillAmount,utilityBillDate,id)
                databaseRef.child(id).setValue(utility).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(this, "Your Data Added successfully", Toast.LENGTH_SHORT).show()

                        binding.utilityBillName.text.clear()
                        binding.utilityBillAmount.text.clear()
                        binding.utilityBillDate.text.clear()

                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
                intent = Intent(applicationContext, UtilityFetchingActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}