package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.databinding.ActivityEditGoalBinding
import com.example.personal_finance_management_app.databinding.ActivityEditSalaryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditSalary : AppCompatActivity() {
    private lateinit var binding: ActivityEditSalaryBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSalaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("salary")

        //fetch data from the intent
        var salary = intent.getStringExtra("totSalary")

        binding.etSalary.setText(salary)

        binding.btnSave.setOnClickListener {

            val salary = binding.etSalary.text.toString()


            val map = HashMap<String, Any>()

            //add data to hashMap
            map["amount"] = salary


            //update database from hashMap
            databaseRef.child(uid!!).updateChildren(map).addOnCompleteListener {
                if (it.isSuccessful) {
                    intent = Intent(applicationContext, SetGoals::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
            finish()
        }


    }
}