package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()


        binding.SendButton.setOnClickListener() {
            val email = binding.TextForgotEmailAddress.text.toString()
            if(email.isEmpty()){
                binding.TextForgotEmailAddress.error = "Enter your email address"
            } else {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this, "Email Send Successfully !!! Check Your Email", Toast.LENGTH_SHORT).show()
                            intent = Intent(applicationContext, Login::class.java)
                            startActivity(intent)

                            finish()
                        }else{
                            Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}