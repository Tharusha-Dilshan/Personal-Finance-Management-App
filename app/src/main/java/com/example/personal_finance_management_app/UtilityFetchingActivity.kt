package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personal_finance_management_app.Adapters.HealthAdapter
import com.example.personal_finance_management_app.Adapters.PortfolioAdapter
import com.example.personal_finance_management_app.Adapters.UtilityAdapter
import com.example.personal_finance_management_app.DataClasses.Asset
import com.example.personal_finance_management_app.DataClasses.HealthModel
import com.example.personal_finance_management_app.DataClasses.UtilityModel
import com.example.personal_finance_management_app.databinding.ActivityHealthFetchingBinding
import com.example.personal_finance_management_app.databinding.ActivityPortfolioBinding
import com.example.personal_finance_management_app.databinding.ActivityUtilityFetchingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class UtilityFetchingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUtilityFetchingBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseRef:DatabaseReference
    private lateinit var uid:String
    private var hList = ArrayList<UtilityModel>()
    private lateinit var adapter: UtilityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUtilityFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addHealth.setOnClickListener{
            intent = Intent(applicationContext, UtilityInsertionActivity::class.java)
            startActivity(intent)
        }
        binding.backBtn.setOnClickListener{
            intent = Intent(applicationContext, ExpensesActivity::class.java)
            startActivity(intent)
        }

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("utility").child(uid)
//        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show()

        var recyclerView = binding.recyclerView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        //addDataToList()
        adapter = UtilityAdapter(hList)
        recyclerView.adapter = adapter

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                hList.clear()
                for ( frSnapshot in snapshot.children){
                    val fr = frSnapshot.getValue(UtilityModel::class.java)!!
                    if( fr != null){
                        hList.add(fr)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UtilityFetchingActivity, error.message, Toast.LENGTH_SHORT).show()
            }

        })

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListener(object: UtilityAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, UtilityDetailsActivity::class.java).also {
                    it.putExtra("utilityName",hList[position].utilityName)
                    it.putExtra("utilityAmount", hList[position].utilityAmount)
                    it.putExtra("utilityDate", hList[position].utilityDate)
                    it.putExtra("utilityId", hList[position].utilityId)
                    startActivity(it)
                }
            }

        })

    }
    private fun addDataToList(){
        hList.add(UtilityModel("Water Bill","2000","2023/04/30"))
    }
}