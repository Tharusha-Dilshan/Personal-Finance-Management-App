package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personal_finance_management_app.Adapters.SugAdapter
import com.example.personal_finance_management_app.DataClasses.SuggestionModel
import com.example.personal_finance_management_app.databinding.ActivityFinanceSuggestionsFetchingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FinanceSuggestionFetching : AppCompatActivity() {

    private lateinit var binding: ActivityFinanceSuggestionsFetchingBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid:String
    private var mList = ArrayList<SuggestionModel>()
    private lateinit var adapter: SugAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinanceSuggestionsFetchingBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("FinanceSuggestions").child(uid)
        //databaseRef = FirebaseDatabase.getInstance().getReference("AllFinSuggestions")
//        Toast.makeText(this, uid, Toast.LENGTH_SHORT).show()

        var recyclerView = binding.rvSuggestion

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        //addDataToList()
        adapter = SugAdapter(mList)
        recyclerView.adapter = adapter

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( frSnapshot in snapshot.children){
                    val fr = frSnapshot.getValue(SuggestionModel::class.java)!!
                    if( fr != null){
                        mList.add(fr)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FinanceSuggestionFetching, error.message, Toast.LENGTH_SHORT).show()
            }
        })

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListener(object:SugAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {


                intent = Intent(applicationContext, FinanceSuggestionDetails::class.java).also {
                    it.putExtra("sugId", mList[position].sugId)
                    it.putExtra("bankName", mList[position].bankName)
                    it.putExtra("finType", mList[position].finType)
                    it.putExtra("suggetion", mList[position].suggetion)

                    startActivity(it)
                }
                finish()
            }
        }

        )

        }
    }
