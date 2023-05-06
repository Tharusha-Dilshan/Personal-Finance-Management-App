package com.example.personal_finance_management_app.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personal_finance_management_app.DataClasses.HealthModel
import com.example.personal_finance_management_app.R

class HealthAdapter (var hList: List<HealthModel>) :
    RecyclerView.Adapter<HealthAdapter.ViewHolder>() {

    private lateinit var hListener : onItemClickListener

    //Setting up onClick listener interface
    interface onItemClickListener{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        hListener = listener
    }

    inner class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val healthName: TextView = itemView.findViewById(R.id.healthName)
        val healthAmount: TextView = itemView.findViewById(R.id.healthAmount)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expenses_health_item, parent, false)

        return ViewHolder(view, hListener)
    }

    override fun getItemCount(): Int {
        return hList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.healthName.text = hList[position].healthName
        holder.healthAmount.text = hList[position].healthAmount

    }
}