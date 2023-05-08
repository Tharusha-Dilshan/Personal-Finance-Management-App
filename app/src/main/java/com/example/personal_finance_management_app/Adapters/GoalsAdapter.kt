package com.example.personal_finance_management_app.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.personal_finance_management_app.DataClasses.GolsModel
import com.example.personal_finance_management_app.R

class GoalsAdapter(var mList: List<GolsModel>) :
    RecyclerView.Adapter<GoalsAdapter.DonationsViewHolder>() {

    private lateinit var mListner : onItemClickListner

    //Setting up onClick listner interface
    interface onItemClickListner{
        fun onItemClick( position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListner = listner
    }

    inner class DonationsViewHolder(itemView: View, listner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {
        val goal: TextView = itemView.findViewById(R.id.tvGoal)
        val cat: TextView = itemView.findViewById(R.id.tvCat)
        //val edtBtn: ImageView = itemView.findViewById(R.id.EditBtn)
        init{
            itemView.setOnClickListener {
                listner.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item_set_goals, parent, false)
        return DonationsViewHolder(view, mListner)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: DonationsViewHolder, position: Int) {
        holder.goal.text = mList[position].amount
        holder.cat.text = mList[position].name
    }


}
