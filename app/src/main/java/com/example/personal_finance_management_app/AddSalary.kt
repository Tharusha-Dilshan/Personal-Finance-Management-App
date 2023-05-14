package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.DataClasses.Salary
import com.example.personal_finance_management_app.databinding.ActivityAddSalaryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddSalary : AppCompatActivity() {
    private lateinit var binding: ActivityAddSalaryBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSalaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("salary").child(uid)


        binding.btnSave.setOnClickListener {

            var salary = binding.etSalary.text.toString()


            if ( salary.isEmpty() ) {
                if (salary.isEmpty()) {
                    binding.etSalary.error = "Enter Category"
                }

            } else {
                //Id for new record
                var id = databaseRef.push().key!!
                //create a FundraisingData object
                val request = Salary( salary,uid,id,)
                databaseRef.setValue(request).addOnCompleteListener {
                    if (it.isSuccessful){

                        Toast.makeText(this, "Salary added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
                intent = Intent(applicationContext, SetGoals::class.java)
                startActivity(intent)
                finish()
            }

        }



    }
}