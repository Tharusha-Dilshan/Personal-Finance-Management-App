package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.databinding.ActivityFinanceSuggestionsEditBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FinanceSuggestionEdit : AppCompatActivity() {

    private lateinit var binding:ActivityFinanceSuggestionsEditBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var databaseRef2: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinanceSuggestionsEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("FinanceSuggestions").child(uid)
        databaseRef2 = FirebaseDatabase.getInstance().getReference("AllFinSuggestions")


        val sugId = intent.getStringExtra("sugId")
        val bankName = intent.getStringExtra("bankName")
        val finType = intent.getStringExtra("finType")
        val suggestion = intent.getStringExtra("suggetion")
        println(bankName)
        //bind values to editTexts
        binding.etBankName.setText(bankName)
        binding.etFinType.setText(finType)
        binding.etSuggestion.setText(suggestion)

        binding.btnUpdateData.setOnClickListener {
            var bankName=binding.etBankName.text.toString()
            var finType=binding.etFinType.text.toString()
            var suggestion=binding.etSuggestion.text.toString()


            val map = HashMap<String,Any>()

            //add data to hashMap
            map["bankName"] = bankName
            map["finType"] = finType
            map["suggetion"] = suggestion



            //update database from hashMap
            databaseRef.child(sugId!!).updateChildren(map).addOnCompleteListener {
                if( it.isSuccessful){
                    intent = Intent(applicationContext, FinanceSuggestionFetching::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Suggestion Updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
            databaseRef2.child(sugId!!).updateChildren(map).addOnCompleteListener {

            }

        }

    }
}