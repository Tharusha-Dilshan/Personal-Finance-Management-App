package com.example.personal_finance_management_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.personal_finance_management_app.DataClasses.User
import com.example.personal_finance_management_app.databinding.ActivityHomePageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.lang.System.exit


class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        //println(uid)
        databaseRef = FirebaseDatabase.getInstance().getReference("users")
        if(uid.isNotEmpty()){
            getUserData()
        }



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
            finish()
            // Finish the current activity to prevent user from returning using the back button
            onBackPressed(System.exit(0))
        }
        binding.logOutTxt.setOnClickListener{
            // Clear any saved user session data or preferences
            clearUserSession()
            finish()
            // Launch LoginActivity to start a new user session
            intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)

            // Finish the current activity to prevent user from returning using the back button

        }

    }

    private fun onBackPressed(exit: Unit) {

    }

    private fun getUserData() {
        databaseRef.child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user = snapshot.getValue(User::class.java)!!
                binding.userName.text = "Hello "+ user.name+"..!!"
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun clearUserSession() {

        // Sign the user out of Firebase
        auth.signOut()

        // TODO: Clear any saved user session data or preferences
    }
//    @Override
//    public override fun onBackPressed() {
//       System.exit(0)
//    }

}

