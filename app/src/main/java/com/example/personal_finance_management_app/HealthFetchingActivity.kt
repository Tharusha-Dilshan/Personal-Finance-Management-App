package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personal_finance_management_app.Adapters.HealthAdapter
import com.example.personal_finance_management_app.Adapters.PortfolioAdapter
import com.example.personal_finance_management_app.DataClasses.Asset
import com.example.personal_finance_management_app.DataClasses.HealthModel
import com.example.personal_finance_management_app.databinding.ActivityHealthFetchingBinding
import com.example.personal_finance_management_app.databinding.ActivityPortfolioBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HealthFetchingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHealthFetchingBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseRef:DatabaseReference
    private lateinit var uid:String
    private var hList = ArrayList<HealthModel>()
    private lateinit var adapter: HealthAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHealthFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addHealth.setOnClickListener{
            intent = Intent(applicationContext, HealthInsertionActivity::class.java)
            startActivity(intent)
        }

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("health").child(uid)
//        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show()

        var recyclerView = binding.recyclerView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        //addDataToList()
        adapter = HealthAdapter(hList)
        recyclerView.adapter = adapter

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                hList.clear()
                for ( frSnapshot in snapshot.children){
                    val fr = frSnapshot.getValue(HealthModel::class.java)!!
                    if( fr != null){
                        hList.add(fr)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HealthFetchingActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListener(object: HealthAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
            }

        })

    }
    private fun addDataToList(){
        hList.add(HealthModel("Nawaloka Bill","200000","2023/04/23"))
    }



}