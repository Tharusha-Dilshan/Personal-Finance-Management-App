package com.example.personal_finance_management_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.personal_finance_management_app.databinding.ActivityViewAssetBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ViewAsset : AppCompatActivity() {
    private lateinit var binding: ActivityViewAssetBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAssetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val assType = intent.getStringExtra("type")
        val accNum = intent.getStringExtra("accnum")
        val amount = intent.getStringExtra("amount")
        val bankName = intent.getStringExtra("bname")
        val duration = intent.getStringExtra("duration")
        val intrest = intent.getStringExtra("intrest")

        var amountCal=amount!!.toDouble()
        var intrestCal=intrest!!.toDouble()/100.00
        var durationCal=duration!!.toDouble()
        var TotIntrest= durationCal*intrestCal*amountCal
        //bind values to editTexts
        binding.tvAssetType.text=assType
        binding.tvAccNo.text=accNum
        binding.tvBName.text=bankName
        binding.tvInterst.text=intrest
        binding.tvDuration.text=duration
        binding.tvAmount.text=amount
        binding.tvTotIntrest.text=TotIntrest.toString()





    }
}