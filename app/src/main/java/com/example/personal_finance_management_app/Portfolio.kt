package com.example.personal_finance_management_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.personal_finance_management_app.Adapters.PortfolioAdapter
import com.example.personal_finance_management_app.DataClasses.Asset
import com.example.personal_finance_management_app.databinding.ActivityPortfolioBinding
import com.google.android.material.internal.ContextUtils.getActivity

class Portfolio : AppCompatActivity() {

    private lateinit var binding: ActivityPortfolioBinding
    private var mList = ArrayList<Asset>()
    private lateinit var adapter: PortfolioAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPortfolioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var recyclerView = binding.recyclerView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        addDataToList()
        adapter = PortfolioAdapter(mList)
        recyclerView.adapter = adapter

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: PortfolioAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
               }

        })








    }
    private fun addDataToList(){
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000","",))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000","",))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000","",))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000","",))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000","",))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000","",))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000","",))
        mList.add(Asset("200011452782","Sampath Bank","Fixed Deposit","","","Rs.1000000","",))


    }



}