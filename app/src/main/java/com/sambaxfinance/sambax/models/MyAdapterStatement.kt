package com.sambaxfinance.sambax.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R

class MyAdapterStatement(val context:Context, val transaction_list: List<StatementResponseModel>):RecyclerView.Adapter<MyAdapterStatement.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        //var transaction_id: TextView
        var amount: TextView
        var transaction_type: TextView
        var created_at: TextView
        var from_amount_statement: TextView
        var to_amount_statement: TextView
        var made_by_statement: TextView

        init {
            //transaction_id = itemView.findViewById(R.id.transaction_id)
            amount = itemView.findViewById(R.id.transaction_amount)
            transaction_type = itemView.findViewById(R.id.transaction_type)
            created_at = itemView.findViewById(R.id.transaction_created_at)
            from_amount_statement = itemView.findViewById(R.id.from_amount_statememt)
            to_amount_statement = itemView.findViewById(R.id.to_amount_statement)
            made_by_statement = itemView.findViewById(R.id.made_by_statement)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items_statement, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.transaction_id.text = "Transaction ID: " + transaction_list[position].id.toString()
        holder.amount.text = "Amount: " + transaction_list[position].amount.toString()
        holder.from_amount_statement.text = "From: " + transaction_list[position].old_balance.toString()
        holder.to_amount_statement.text = "To: " + transaction_list[position].new_balance.toString()
        holder.transaction_type.text = "Transaction Type: " + transaction_list[position].transaction_type
        holder.made_by_statement.text = "Made by: " + transaction_list[position].made_by
        holder.created_at.text = "Created at: " + transaction_list[position].created_at
    }

    override fun getItemCount(): Int {
        return transaction_list.size
    }
}