package com.example.personal_finance_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personal_finance_management_app.Adapters.GoalsAdapter
import com.example.personal_finance_management_app.DataClasses.GolsModel
import com.example.personal_finance_management_app.databinding.ActivitySetGoalsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SetGoals : AppCompatActivity() {
    private lateinit var binding: ActivitySetGoalsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<GolsModel>()
    private lateinit var adapter: GoalsAdapter
    var totalAmount = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetGoalsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("setGoals").child(uid)

        recyclerView = binding.recyclerview

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        //addDataToList()
        retrieveData()
        adapter = GoalsAdapter(mList)
        recyclerView.adapter = adapter

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: GoalsAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, EditGoal::class.java).also {
                    it.putExtra("name", mList[position].name)
                    it.putExtra("amount", mList[position].amount)
                    it.putExtra("id", mList[position].id)
                    startActivity(it)
                }
            }
        })

        binding.btnAdd.setOnClickListener {
            intent = Intent(applicationContext, NewCatogory::class.java)
            startActivity(intent)
        }

    }

    private fun retrieveData() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val req = snapshot.getValue(GolsModel::class.java)!!
                    if( req != null){
                        mList.add(req)
                    }
                }
                adapter.notifyDataSetChanged()
                updateTotalAmount()

                var sTotal=totalAmount.toString()

                binding.checkBtn.setOnClickListener{
                    intent = Intent(applicationContext, SetGoalSalaryCheck::class.java).also {
                        it.putExtra("totalAmount",sTotal)
                        startActivity(it)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SetGoals, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun updateTotalAmount() {


        for (expense in mList) {
            val amount = expense.amount!!.toDouble()
            if (amount != null) {
                totalAmount += amount
            }
        }

        binding.tvTotGoal.text = String.format("%.2f", totalAmount)
    }

    private fun addDataToList(){
        mList.add(GolsModel("","test","12500", ""))
        mList.add(GolsModel("","test","12500", ""))
        mList.add(GolsModel("","test","12500", ""))
    }
}