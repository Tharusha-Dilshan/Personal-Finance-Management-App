package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personal_finance_management_app.databinding.ActivityPortAddAssetBinding
import com.example.personal_finance_management_app.databinding.ActivityPortEditAssetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PortEditAsset : AppCompatActivity() {
    private lateinit var binding:ActivityPortEditAssetBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortEditAssetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("portfolioAssets").child(uid)




        val assType = intent.getStringExtra("type")
        val accNum = intent.getStringExtra("accnum")
        val amount = intent.getStringExtra("amount")
        val bankName = intent.getStringExtra("bname")
        val duration = intent.getStringExtra("duration")
        val intrest = intent.getStringExtra("intrest")
        val reqID = intent.getStringExtra("reqID")

        binding.tvAssetType.text=assType
        binding.editAccNo.setText(accNum)
        binding.editAmount.setText(amount)
        binding.editDuration.setText(duration)
        binding.editIntrest.setText(intrest)
        binding.editBank.setText(bankName)


        binding.saveBtn.setOnClickListener {
            var accnum=binding.editAccNo.text.toString()
            var amount=binding.editAmount.text.toString()
            var duration=binding.editDuration.text.toString()
            var intrest=binding.editIntrest.text.toString()
            var bank=binding.editBank.text.toString()

            val map = HashMap<String,Any>()

            //add data to hashMap
            map["accNumber"] = accnum
            map["bankName"] = bank
            map["intrestRate"] = intrest
            map["duration"] = duration
            map["amount"] = amount


            //update database from hashMap
            databaseRef.child(reqID!!).updateChildren(map).addOnCompleteListener {
                if( it.isSuccessful){
                    intent = Intent(applicationContext, Portfolio::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Asset updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

        }



    }
}