package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
        val reqID = intent.getStringExtra("reqId")

        var amountCal=amount!!.toDouble()
        var intrestCal=intrest!!.toDouble()/100.00
        var durationCal=duration!!.toDouble()/12
        var TotIntrest= Math.round(durationCal*intrestCal*amountCal)
        //bind values to editTexts
        binding.tvAssetType.text=assType
        binding.tvAccNo.text=accNum
        binding.tvBName.text=bankName
        binding.tvInterst.text=intrest
        binding.tvDuration.text=duration
        binding.tvAmount.text=amount
        binding.tvTotIntrest.text=TotIntrest.toString()

        binding.EditBtn.setOnClickListener {
            intent = Intent(applicationContext, PortEditAsset::class.java).also {
                it.putExtra("type",assType)
                it.putExtra("accnum", accNum)
                it.putExtra("amount", amount)
                it.putExtra("bname", bankName)
                it.putExtra("duration", duration)
                it.putExtra("intrest", intrest)
                it.putExtra("reqID", reqID)



                startActivity(it)
            }
            finish()
        }

        binding.deleteBtn.setOnClickListener {
            databaseRef.child(reqID!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, Portfolio::class.java)
                    startActivity(intent)
                }
            }
            finish()
        }


    }
}