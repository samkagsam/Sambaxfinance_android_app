package com.sambaxfinance.sambax.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R

class MyAdapterLtgMembersWithLoans(val context: Context, val members_list: List<LtgMembersWithLoansResponseModel>):RecyclerView.Adapter<MyAdapterLtgMembersWithLoans.ViewHolder>()  {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ltg_loan_member_name: TextView
        var ltg_loan_member_phone_number: TextView
        var ltg_member_loan_balance: TextView
        var ltg_loan_expiry_date: TextView


        init {
            ltg_loan_member_name = itemView.findViewById(R.id.ltg_loan_member_name)
            ltg_loan_member_phone_number = itemView.findViewById(R.id.ltg_loan_member_phone_number)
            ltg_member_loan_balance = itemView.findViewById(R.id.ltg_member_loan_balance)
            ltg_loan_expiry_date = itemView.findViewById(R.id.ltg_loan_expiry_date)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items_ltg_group_members_with_loans, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ltg_loan_member_name.text = "Name: " + members_list[position].name
        holder.ltg_loan_member_phone_number.text = "Phone Number: " + members_list[position].phone_number
        holder.ltg_member_loan_balance.text = "Loan Balance: " + members_list[position].loan_balance
        holder.ltg_loan_expiry_date.text = "Expiry Date: " + members_list[position].expiry_date

    }

    override fun getItemCount(): Int {
        return members_list.size
    }
}