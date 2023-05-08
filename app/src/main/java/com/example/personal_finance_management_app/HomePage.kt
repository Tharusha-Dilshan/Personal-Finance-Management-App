package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personal_finance_management_app.databinding.ActivityHomePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().getReference("users").child(uid)

        val name = intent.getStringExtra("name")
        //print value in console (still getting null value)
        println(name)

        //bind values to editTexts
        binding.userName.text = name

        binding.homeFinanceImg.setOnClickListener {
            intent = Intent(applicationContext, FinanceActivity::class.java)
            startActivity(intent)
        }
        binding.homeExpenseImg.setOnClickListener {
            intent = Intent(applicationContext, ExpensesActivity::class.java)
            startActivity(intent)
        }
        binding.homePortfolioImg.setOnClickListener {
            intent = Intent(applicationContext, Portfolio::class.java)
            startActivity(intent)
        }

        binding.homeLogOutImg.setOnClickListener{
            // Clear any saved user session data or preferences
            clearUserSession()

            // Launch LoginActivity to start a new user session
            intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)

            // Finish the current activity to prevent user from returning using the back button
            finish()
        }
        binding.logOutTxt.setOnClickListener{
            // Clear any saved user session data or preferences
            clearUserSession()

            // Launch LoginActivity to start a new user session
            intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)

            // Finish the current activity to prevent user from returning using the back button
            finish()
        }

    }
    private fun clearUserSession() {

        // Sign the user out of Firebase
        auth.signOut()

        // TODO: Clear any saved user session data or preferences
    }
}