package com.example.personal_finance_management_app

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.DataClasses.Asset
import com.example.personal_finance_management_app.databinding.ActivityPortAddAssetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PortAddAsset : AppCompatActivity() {
    private lateinit var binding: ActivityPortAddAssetBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortAddAssetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("portfolioAssets").child(uid)

        binding.addBtn.setOnClickListener{
            var type=binding.spinner.selectedItem.toString()
            var accNo=binding.addAccNo.text.toString()
            var amount=binding.addAmount.text.toString()
            var duration=binding.addDuration.text.toString()
            var intrest=binding.addIntrest.text.toString()
            var bank=binding.addBank.text.toString()

            if(accNo.isEmpty() ||amount.isEmpty() ||duration.isEmpty() ||intrest.isEmpty() ||bank.isEmpty()){

                if(accNo.isEmpty()){
                    binding.addAccNo.error = "Enter your account number"
                }
                if(amount.isEmpty()){
                    binding.addAmount.error = "Enter Amount"
                }
                if(duration.isEmpty()){
                    binding.addDuration.error = "Enter Duration"
                }
                if(intrest.isEmpty()){
                    binding.addIntrest.error = "Enter Interest"
                }
                if(bank.isEmpty()){
                    binding.addBank.error = "Enter Bank"
                }
                Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show()

            }else if( binding.spinner.selectedItemPosition == 0 ) {
                Toast.makeText(this, "Please select your asset type.", Toast.LENGTH_SHORT).show()
            }else{

                //Id for new record
                var id = databaseRef.push().key!!
                //create a Asset object
                val asset = Asset( accNo,bank,type,intrest,duration,amount,id)
                databaseRef.child(id).setValue(asset).addOnCompleteListener {
                    if (it.isSuccessful){

                        Toast.makeText(this, "Your asset added successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
//                intent = Intent(applicationContext, Portfolio::class.java)
//                startActivity(intent)
                finish()
            }
        }
    }
}