package com.sambaxfinance.sambax.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R

class MyAdapterLoanStatement(
    val context: Context,
    private val paymentsList: List<Payment>
) : RecyclerView.Adapter<MyAdapterLoanStatement.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val loan_id: TextView = itemView.findViewById(R.id.loan_id)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val create_date: TextView = itemView.findViewById(R.id.create_date)
        val old_loan_balance: TextView = itemView.findViewById(R.id.old_loan_balance)
        val new_loan_balance: TextView = itemView.findViewById(R.id.new_loan_balance)
        val transaction_type: TextView = itemView.findViewById(R.id.transaction_type)
        val made_by: TextView = itemView.findViewById(R.id.made_by)
        val description: TextView = itemView.findViewById(R.id.description)
        val old_installment_balance: TextView = itemView.findViewById(R.id.old_installment_balance)
        val new_installment_balance: TextView = itemView.findViewById(R.id.new_installment_balance)
        val old_arrears_balance: TextView = itemView.findViewById(R.id.old_arrears_balance)
        val new_arrears_balance: TextView = itemView.findViewById(R.id.new_arrears_balance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.row_items_loan_statement, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val payment = paymentsList[position]

        holder.loan_id.text = "Loan Id: ${payment.id}"
        holder.amount.text = "Amount: ${payment.amount}"
        holder.create_date.text = "Create Date: ${payment.created_at}"
        holder.old_loan_balance.text = "Old Loan Balance: ${payment.old_balance}"
        holder.new_loan_balance.text = "New Loan Balance: ${payment.new_balance}"
        holder.transaction_type.text = "Transaction Type: ${payment.transaction_type}"
        holder.made_by.text = "Made By: ${payment.made_by}"
        holder.description.text = "Description: ${payment.description}"
        holder.old_installment_balance.text = "Old Installment Balance: ${payment.old_installment_balance}"
        holder.new_installment_balance.text = "New Installment Balance: ${payment.new_installment_balance}"
        holder.old_arrears_balance.text = "Old Arrears Balance: ${payment.old_arrears_balance}"
        holder.new_arrears_balance.text = "New Arrears Balance: ${payment.new_arrears_balance}"
    }

    override fun getItemCount(): Int {
        return paymentsList.size
    }
}
