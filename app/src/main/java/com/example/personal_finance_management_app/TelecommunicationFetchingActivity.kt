package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personal_finance_management_app.Adapters.HealthAdapter
import com.example.personal_finance_management_app.Adapters.PortfolioAdapter
import com.example.personal_finance_management_app.Adapters.TelecommunicationAdapter
import com.example.personal_finance_management_app.DataClasses.Asset
import com.example.personal_finance_management_app.DataClasses.HealthModel
import com.example.personal_finance_management_app.DataClasses.TelecommunicationModel
import com.example.personal_finance_management_app.databinding.ActivityHealthFetchingBinding
import com.example.personal_finance_management_app.databinding.ActivityPortfolioBinding
import com.example.personal_finance_management_app.databinding.ActivityTelecommunicationFetchingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TelecommunicationFetchingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTelecommunicationFetchingBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseRef:DatabaseReference
    private lateinit var uid:String
    private var hList = ArrayList<TelecommunicationModel>()
    private lateinit var adapter: TelecommunicationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelecommunicationFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addTel.setOnClickListener{
            intent = Intent(applicationContext,TelecommunicationInsertionActivity::class.java)
            startActivity(intent)
        }

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("telecommunication").child(uid)
//        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show()

        var recyclerView = binding.recyclerView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        //addDataToList()
        adapter = TelecommunicationAdapter(hList)
        recyclerView.adapter = adapter

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                hList.clear()

                var totalAmount = 0.0 // Initialize the totalAmount variable to zero

                for ( frSnapshot in snapshot.children){
                    val fr = frSnapshot.getValue(TelecommunicationModel::class.java)!!
                    if( fr != null){
                        hList.add(fr)
                        totalAmount += fr.telAmount!!.toDouble() // Add the amount of each health bill to the totalAmount variable
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TelecommunicationFetchingActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListener(object: TelecommunicationAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
            }

        })

    }
    private fun addDataToList(){
        hList.add(TelecommunicationModel("Dialog Router Bill","2300","2023/04/30"))
    }

}


