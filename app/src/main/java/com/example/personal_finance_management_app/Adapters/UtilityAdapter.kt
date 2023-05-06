package com.example.personal_finance_management_app.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personal_finance_management_app.DataClasses.UtilityModel
import com.example.personal_finance_management_app.R

class UtilityAdapter (var uList: List<UtilityModel>) :
    RecyclerView.Adapter<UtilityAdapter.ViewHolder>() {

    private lateinit var uListener : onItemClickListener

    //Setting up onClick listener interface
    interface onItemClickListener{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        uListener = listener
    }

    inner class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val utilityName: TextView = itemView.findViewById(R.id.utilityName)
        val utilityAmount: TextView = itemView.findViewById(R.id.utilityAmount)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expenses_utility_item, parent, false)

        return ViewHolder(view, uListener)
    }

    override fun getItemCount(): Int {
        return uList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.utilityName.text = uList[position].utilityName
        holder.utilityAmount.text = uList[position].utilityAmount

    }
}