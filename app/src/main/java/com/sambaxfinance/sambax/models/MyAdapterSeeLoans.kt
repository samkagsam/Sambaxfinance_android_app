package com.sambaxfinance.sambax.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R

class MyAdapterSeeLoans (val context: Context, val request_list: List<SeeLoansResponseModel>):RecyclerView.Adapter<MyAdapterSeeLoans.ViewHolder>() {
    var onItemClick:((SeeLoansResponseModel) -> Unit)? = null


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var loan_id: TextView
        var loan_balance: TextView
        var installment_amount: TextView
        var installment_balance: TextView
        var arrears: TextView
        var installment_day: TextView
        var create_date: TextView
        var expiry_date: TextView
        var btnViewStatement: Button



        init {
            loan_id = itemView.findViewById(R.id.loan_id)
            loan_balance = itemView.findViewById(R.id.loan_balance)
            installment_amount = itemView.findViewById(R.id.installment_amount)
            installment_balance = itemView.findViewById(R.id.installment_balance)
            arrears = itemView.findViewById(R.id.arrears)
            installment_day = itemView.findViewById(R.id.installment_day)
            create_date = itemView.findViewById(R.id.create_date)
            expiry_date = itemView.findViewById(R.id.create_date)
            btnViewStatement = itemView.findViewById(R.id.btnViewStatement)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items_see_loans, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.loan_id.text = "Loan Id:" + request_list[position].loan_id
        holder.loan_balance.text = "Loan Balance: " + request_list[position].loan_balance
        holder.installment_amount.text = "Installment Amount: " + request_list[position].installment_amount
        holder.installment_balance.text = "Installment Balance: " + request_list[position].installment_balance
        holder.arrears.text = "Arrears: " + request_list[position].arrears
        holder.installment_day.text = "Installment day: " + request_list[position].installment_day
        holder.create_date.text = "Create date: " + request_list[position].create_date
        holder.expiry_date.text = "Expiry date: " + request_list[position].expiry_date




        val request_credentials = request_list[position]

        holder.btnViewStatement.setOnClickListener {
            onItemClick?.invoke(request_credentials)

        }


    }

    override fun getItemCount(): Int {
        return request_list.size
    }

}