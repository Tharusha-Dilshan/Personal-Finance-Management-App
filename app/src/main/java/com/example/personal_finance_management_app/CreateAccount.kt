package com.example.personal_finance_management_app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import com.example.personal_finance_management_app.DataClasses.User
import com.example.personal_finance_management_app.databinding.ActivityCreateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class CreateAccount : AppCompatActivity() {
    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private var imageUri : Uri? =null

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri =it
        binding.homeAvatarImg.setImageURI(imageUri)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing auth and database variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.homeAvatarImg.setOnClickListener{
            selectImage.launch("image/*")

        }


        binding.SingInButton.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPass.text.toString()
            val confirmPwd = binding.etConPass.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPwd.isEmpty() ) {
                if (name.isEmpty()) {
                    binding.etName.error = "Enter your name"
                }
                if (email.isEmpty()) {
                    binding.etEmail.error = "Enter your email"
                }

                if (password.isEmpty()) {
                    binding.etPass.error = "Enter your password"
                }
                if (confirmPwd.isEmpty()) {
                    binding.etConPass.error = "Re-enter your password"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()

            }
            //validate email pattern
            else if (!email.matches(emailPattern.toRegex())) {
                binding.etEmail.error = "Enter a valid email address"
            }

            //validate passwords
            else if (password.length < 7) {
                binding.etPass.error = "Password must be at least 7 characters."
            } else if (confirmPwd != password) {
                binding.etConPass.error = "Passwords do not match. Please try again."
            } else {

                //uploadImage()

                /* val storageRef = FirebaseStorage.getInstance().getReference("users")
                    .child(auth.currentUser!!.uid).child("users.jpg")


                storageRef.putFile(imageUri!!).addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener {
                               val image = it.toString() */

                        //Toast.makeText(this, "All good", Toast.LENGTH_SHORT).show()
                        //creating an auth for the user
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                            if (it.isSuccessful) {
                                //store user details in the database
                                val databaseRef =
                                    database.reference.child("users").child(auth.currentUser!!.uid)
                                val user: User = User(name, email, auth.currentUser!!.uid )
                                databaseRef.setValue(user).addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        //redirect user to login activity
                                        val intent = Intent(this, Login::class.java)
                                        startActivity(intent)
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Something went wrong, try again",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                            } else {
                                Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }



               /*
                    }.addOnFailureListener{
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
                */






            }


        }

    }




}