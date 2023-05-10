package com.example.personal_finance_management_app


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personal_finance_management_app.databinding.ActivityLoginBinding
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing auth
        auth = FirebaseAuth.getInstance()

        //Forgot password
        binding .tvForgotPassword.setOnClickListener() {
            intent = Intent(applicationContext, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        //set onclick listner on login button
        binding.LoginButton.setOnClickListener() {

            val email = binding.TextEmailAddress.text.toString()
            val password = binding.TextPassword.text.toString()

            //checking if the input fields are empty
            if(email.isEmpty() || password.isEmpty()){

                if(email.isEmpty()){
                    binding.TextEmailAddress.error = "Enter your email address"
                }
                if(password.isEmpty()){
                    binding.TextPassword.error = "Enter your password"
                }
                Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show()

            } else if (!email.matches(emailPattern.toRegex())){
                //validate email pattern
                binding.TextEmailAddress.error = "Enter a valid email address"
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()

            } else if (password.length < 7){
                //validate passwords
                binding.TextPassword.error = "Password must be at least 7 characters."
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()

            } else{
                //Log in
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        intent = Intent(applicationContext, HomePage::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            finish()
        }
        binding.RegistrationButton1.setOnClickListener{
            intent = Intent(applicationContext, CreateAccount::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun onStart() {
        super.onStart()

        if(auth.currentUser != null){
            val intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
    }
}