package com.sambaxfinance.sambax.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R

class MyAdapterTotalRoscaMemberContribution(val context: Context, val members_list: List<TotalRoscaMemberContributionResponseModel>):RecyclerView.Adapter<MyAdapterTotalRoscaMemberContribution.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var member_first_name: TextView

        var member_phone_number: TextView
        var member_user_week: TextView

        init {
            member_first_name = itemView.findViewById(R.id.member_first_name)

            member_phone_number = itemView.findViewById(R.id.member_phone_number)
            member_user_week = itemView.findViewById(R.id.member_user_week)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items_group_members, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.member_first_name.text = "Name: " + members_list[position].name

        holder.member_phone_number.text = "Phone Number: " + members_list[position].phone_number
        holder.member_user_week.text = "Total Contribution: UgX" + members_list[position].total_contribution
    }

    override fun getItemCount(): Int {
        return members_list.size
    }
}