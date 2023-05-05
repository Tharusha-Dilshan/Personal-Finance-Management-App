package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personal_finance_management_app.Adapters.PortfolioAdapter
import com.example.personal_finance_management_app.DataClasses.Asset
import com.example.personal_finance_management_app.databinding.ActivityPortfolioBinding
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Portfolio : AppCompatActivity() {

    private lateinit var binding: ActivityPortfolioBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseRef:DatabaseReference
    private lateinit var uid:String
    private var mList = ArrayList<Asset>()
    private lateinit var adapter: PortfolioAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortfolioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView8.setOnClickListener{
            intent = Intent(applicationContext, PortAddAsset::class.java)
            startActivity(intent)
        }

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("portfolioAssets").child(uid)
//        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show()

        var recyclerView = binding.recyclerView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        //addDataToList()
        adapter = PortfolioAdapter(mList)
        recyclerView.adapter = adapter

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( frSnapshot in snapshot.children){
                    val fr = frSnapshot.getValue(Asset::class.java)!!
                    if( fr != null){
                        mList.add(fr)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Portfolio, error.message, Toast.LENGTH_SHORT).show()
            }

        })

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: PortfolioAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
               }

        })








    }
    private fun addDataToList(){
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000"))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000"))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000"))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000"))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000"))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000"))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000"))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000"))


    }



}