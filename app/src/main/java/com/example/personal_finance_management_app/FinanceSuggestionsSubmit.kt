package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.DataClasses.SuggestionModel
import com.example.personal_finance_management_app.databinding.ActivityFinanceSuggestionsSubmitBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FinanceSuggestionsSubmit : AppCompatActivity() {
    private lateinit var binding: ActivityFinanceSuggestionsSubmitBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var databaseRef2: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinanceSuggestionsSubmitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("FinanceSuggestions").child(uid)
        databaseRef2 = FirebaseDatabase.getInstance().getReference("AllFinSuggestions")

        binding.sugAddBtn.setOnClickListener{
            var bankName=binding.sugBankName.selectedItem.toString()
            var finType=binding.sugFinanceType.selectedItem.toString()
            var addedSug=binding.sugInputField.text.toString()

            if(bankName.isEmpty() ||finType.isEmpty() ||addedSug.isEmpty()){
                if(addedSug.isEmpty()){
                    binding.sugInputField.error = "Enter your Suggestion"
                }
                Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show()

            }else if( binding.sugBankName.selectedItemPosition == 0 ) {
                Toast.makeText(this, "Please select A Bank.", Toast.LENGTH_SHORT).show()
            }else if( binding.sugFinanceType.selectedItemPosition == 0 ) {
                Toast.makeText(this, "Please select your finance type.", Toast.LENGTH_SHORT).show()
            }

            else{

                //Id for new record
                var id = databaseRef.push().key!!
                //var id2 = databaseRef2.push().key!!
                //create a suggestion object
                val suggestion = SuggestionModel(id,bankName,finType,addedSug,)
                val suggestion2 = SuggestionModel(id,bankName,finType,addedSug,)
                databaseRef.child(id).setValue(suggestion).addOnCompleteListener {
                    if (it.isSuccessful){

                        Toast.makeText(this, "Your Suggestion added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
                databaseRef2.child(id).setValue(suggestion2).addOnCompleteListener {

                }
                intent = Intent(applicationContext, FinanceSuggestionFetching::class.java)
                startActivity(intent)
                finish()
            }

        }
        binding.sugCancelBtn.setOnClickListener{
            binding.sugInputField.text?.clear()

        }
        binding.sugEditBtn.setOnClickListener{
            intent = Intent(applicationContext, FinanceSuggestionFetching::class.java)
            startActivity(intent)

        }
    }
}