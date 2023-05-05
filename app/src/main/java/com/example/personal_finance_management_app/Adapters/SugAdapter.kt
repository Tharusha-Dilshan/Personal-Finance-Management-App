package com.example.personal_finance_management_app.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.personal_finance_management_app.DataClasses.SuggestionModel
import com.example.personal_finance_management_app.R

class SugAdapter(private val sugList: ArrayList<SuggestionModel>) :
    RecyclerView.Adapter<SugAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sugge_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSuggestion = sugList[position]
        holder.tvSuggestion.text = currentSuggestion.suggetion
    }

    override fun getItemCount(): Int {
        return sugList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val tvSuggestion : TextView = itemView.findViewById(R.id.tvSuggestion)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }

    }

}