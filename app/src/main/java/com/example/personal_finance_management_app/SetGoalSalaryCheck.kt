package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.DataClasses.Salary
import com.example.personal_finance_management_app.databinding.ActivitySetGoalSalaryCheckBinding
import com.example.personal_finance_management_app.databinding.ActivityTelecommunicationFetchingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SetGoalSalaryCheck : AppCompatActivity() {
    private lateinit var binding: ActivitySetGoalSalaryCheckBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid:String
    var totSalary=0.0
    var balance=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetGoalSalaryCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("salary")


        val totalGoal = intent.getStringExtra("totalAmount")
        binding.tvTotGoal.text=totalGoal

        binding.backBtn.setOnClickListener {
            intent = Intent(applicationContext, SetGoals::class.java)
            startActivity(intent)

        }
        binding.addBtn.setOnClickListener {
            intent = Intent(applicationContext, AddSalary::class.java)
            startActivity(intent)

        }
        binding.EditBtn.setOnClickListener {
            intent = Intent(applicationContext, EditSalary::class.java)
            startActivity(intent)

        }



        databaseRef.child(auth.currentUser!!.uid).addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to salary data class
                if (snapshot.exists()) {
                    var salary = snapshot.getValue(Salary::class.java)!!

                    binding.tvSalary.text = salary.amount
                    totSalary=salary.amount!!.toDouble()

                    balance=totSalary-totalGoal!!.toFloat()
                    binding.tvBalance.text=balance.toString()
                } else {
                    Toast.makeText(this@SetGoalSalaryCheck, "No data found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SetGoalSalaryCheck, "Faild to fetch salary", Toast.LENGTH_SHORT).show()

            }
        })



    }
}