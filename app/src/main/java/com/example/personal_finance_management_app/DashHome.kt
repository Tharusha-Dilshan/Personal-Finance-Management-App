package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personal_finance_management_app.databinding.ActivityDashHomeBinding
import com.google.firebase.auth.FirebaseAuth

class DashHome : AppCompatActivity() {
    private lateinit var binding: ActivityDashHomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDashHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()

        binding.finimg.setOnClickListener {
            intent = Intent(applicationContext, FinanceActivity::class.java)
            startActivity(intent)
        }
        binding.expensesimg.setOnClickListener {
            intent = Intent(applicationContext, ExpensesActivity::class.java)
            startActivity(intent)
        }
        binding.portimg.setOnClickListener {
            intent = Intent(applicationContext, Portfolio::class.java)
            startActivity(intent)
        }
        binding.goalsBtn.setOnClickListener {
            intent = Intent(applicationContext, SetGoals::class.java)
            startActivity(intent)
        }

        binding.logoutbtn.setOnClickListener{
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