package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.DataClasses.GolsModel
import com.example.personal_finance_management_app.databinding.ActivityNewCatogoryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NewCatogory : AppCompatActivity() {
    private lateinit var binding: ActivityNewCatogoryBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewCatogoryBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("setGoals").child(uid)

        binding.btnSave.setOnClickListener {

            var cat = binding.etName.text.toString()
            var goal = binding.etGoal.text.toString()

            if ( cat.isEmpty() || goal.isEmpty() ) {
                if (cat.isEmpty()) {
                    binding.etName.error = "Enter Category"
                }

                if (goal.isEmpty()) {
                    binding.etGoal.error = "Enter amount"
                }
            } else {
                //Id for new record
                var id = databaseRef.push().key!!
                //create a FundraisingData object
                val request = GolsModel( id,cat,goal,uid)
                databaseRef.child(id).setValue(request).addOnCompleteListener {
                    if (it.isSuccessful){
                        intent = Intent(applicationContext, SetGoals::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Goal added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            finish()
        }
    }
}