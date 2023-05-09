package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.databinding.ActivityFinanceSuggestionDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FinanceSuggestionDetails : AppCompatActivity() {
    private lateinit var binding: ActivityFinanceSuggestionDetailsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var databaseRef2: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinanceSuggestionDetailsBinding.inflate(layoutInflater)
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


        //bind values to editTexts

        binding.tvBankName.text = bankName
        binding.tvFinType.text = finType
        binding.tvSuggestion.text = suggestion


        binding.btnUpdate.setOnClickListener{
            intent = Intent(applicationContext, FinanceSuggestionEdit::class.java).also {
                it.putExtra("sugId", sugId)
                it.putExtra("bankName", bankName)
                it.putExtra("finType", finType)
                it.putExtra("suggetion",suggestion)



                startActivity(it)
            }
        }

        binding.btnDelete.setOnClickListener{
            databaseRef.child(sugId!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Suggestion Deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, FinanceSuggestionFetching::class.java)
                    startActivity(intent)
                }
            }
            databaseRef2.child(sugId!!).removeValue().addOnCompleteListener {}
        }


    }
}