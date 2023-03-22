package com.sambaxfinance.sambax.models


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R

class MyAdapterPayments(val context:Context, val payment_list: List<MyLoanPaymentsResponseModel>):RecyclerView.Adapter<MyAdapterPayments.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var amount: TextView
        var created_at: TextView
        var from_amount_loan: TextView
        var to_amount_loan: TextView
        var transaction_type_loan: TextView
        var made_by_loan: TextView

        init {
            amount = itemView.findViewById(R.id.amount)
            created_at = itemView.findViewById(R.id.created_at_loan)
            from_amount_loan = itemView.findViewById(R.id.from_amout_loan)
            to_amount_loan = itemView.findViewById(R.id.to_amount_loan)
            transaction_type_loan = itemView.findViewById(R.id.transaction_type_loan)
            made_by_loan = itemView.findViewById(R.id.made_by_loan)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items_loan_payments, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.amount.text = "Amount: " + payment_list[position].amount.toString()
        holder.from_amount_loan.text = "From: " + payment_list[position].old_balance.toString()
        holder.to_amount_loan.text = "To: " + payment_list[position].new_balance.toString()
        holder.transaction_type_loan.text = "Transaction type: " + payment_list[position].transaction_type
        holder.made_by_loan.text = "Made by: " + payment_list[position].made_by
        holder.created_at.text = "Created at: " + payment_list[position].created_at
    }

    override fun getItemCount(): Int {
        return payment_list.size
    }
}