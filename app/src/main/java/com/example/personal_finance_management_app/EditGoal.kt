package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.databinding.ActivityEditGoalBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditGoal : AppCompatActivity() {
    private lateinit var binding: ActivityEditGoalBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("setGoals").child(uid)


        //fetch data from the intent
        var name = intent.getStringExtra("name")
        var amount = intent.getStringExtra("amount")
        var id = intent.getStringExtra("id")


        binding.etName.setText(name)
        binding.etGoal.setText(amount)

        binding.btnSave.setOnClickListener {

            val category = binding.etName.text.toString()
            val goal = binding.etGoal.text.toString()

            val map = HashMap<String, Any>()

            //add data to hashMap
            map["name"] = category
            map["amount"] = goal


            //update database from hashMap
            databaseRef.child(id!!).updateChildren(map).addOnCompleteListener {
                if (it.isSuccessful) {
                    //intent = Intent(applicationContext, SetGoals::class.java)
                    //startActivity(intent)
                    Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
            finish()
        }

        binding.deleteBtn.setOnClickListener {
            databaseRef.child(id!!).removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    //intent = Intent(applicationContext, SetGoals::class.java)
                    //startActivity(intent)
                }
            }
            finish()
        }

    }
}