package com.example.personal_finance_management_app.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personal_finance_management_app.DataClasses.Asset
import com.example.personal_finance_management_app.Portfolio
import com.example.personal_finance_management_app.R

class PortfolioAdapter (var mList: List<Asset>) :
    RecyclerView.Adapter<PortfolioAdapter.ViewHolder>() {

    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class ViewHolder(itemView: View, listner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val assetname: TextView = itemView.findViewById(R.id.assetName)
        val assetvalue: TextView = itemView.findViewById(R.id.assetValue)

        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_asset_recycle_view, parent, false)



        return ViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.assetname.text = mList[position].assetType
        holder.assetvalue.text = mList[position].amount

    }


}