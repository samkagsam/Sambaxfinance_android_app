package com.sambaxfinance.sambax.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R

class MyAdapter(val context:Context, val application_list: List<MyLoanApplicationsResponseModel>):RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var application_id: TextView
        var created_at: TextView

        init {
            application_id = itemView.findViewById(R.id.application_id)
            created_at = itemView.findViewById(R.id.created_at)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.application_id.text = "Application ID: " + application_list[position].id.toString()
        holder.created_at.text = "created at: " + application_list[position].created_at
    }

    override fun getItemCount(): Int {
        return application_list.size
    }
}