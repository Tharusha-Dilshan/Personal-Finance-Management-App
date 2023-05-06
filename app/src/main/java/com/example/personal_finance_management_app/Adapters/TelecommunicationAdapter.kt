package com.example.personal_finance_management_app.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personal_finance_management_app.DataClasses.TelecommunicationModel
import com.example.personal_finance_management_app.R

class TelecommunicationAdapter (var hList: List<TelecommunicationModel>) :
    RecyclerView.Adapter<TelecommunicationAdapter.ViewHolder>() {

    private lateinit var hListener : onItemClickListener

    //Setting up onClick listener interface
    interface onItemClickListener{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){
        hListener = listener
    }

    inner class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val teleName: TextView = itemView.findViewById(R.id.teleName)
        val teleAmount: TextView = itemView.findViewById(R.id.teleAmount)

        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expenses_telecommunication_item, parent, false)

        return ViewHolder(view, hListener)
    }

    override fun getItemCount(): Int {
        return hList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.teleName.text = hList[position].telName
        holder.teleAmount.text = hList[position].telAmount

    }
}