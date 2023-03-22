package com.sambaxfinance.sambax.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R

class MyAdapterShowGroupStatement(val context: Context, val transaction_list: List<GroupStatementResponseModel>):RecyclerView.Adapter<MyAdapterShowGroupStatement.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var fullname: TextView
        var userphone_number: TextView
        var amount: TextView

        var created_at: TextView

        init {
            fullname = itemView.findViewById(R.id.gname)
            userphone_number = itemView.findViewById(R.id.gphone_number)
            amount = itemView.findViewById(R.id.Gtransaction_amount)
            created_at = itemView.findViewById(R.id.Gtransaction_created_at)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items_show_group_statement, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fullname.text = "Name: " + transaction_list[position].first_name + " " + transaction_list[position].last_name
        holder.userphone_number.text = "Phone Number: " + transaction_list[position].phone_number
        holder.amount.text = "Amount: " + transaction_list[position].amount
        holder.created_at.text = transaction_list[position].created_at
    }

    override fun getItemCount(): Int {
        return transaction_list.size
    }
}