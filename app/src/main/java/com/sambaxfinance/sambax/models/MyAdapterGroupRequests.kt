package com.sambaxfinance.sambax.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R

class MyAdapterGroupRequests(val context: Context, val request_list: List<GroupRequestResponseModel>):RecyclerView.Adapter<MyAdapterGroupRequests.ViewHolder>() {

    var onItemClick:((GroupRequestResponseModel) -> Unit)? = null
    var onItemClick2:((GroupRequestResponseModel) -> Unit)? = null

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var group_no: TextView
        var group_admin: TextView
        var admin_phone_number: TextView
        var btnApproveRequest: Button
        var btnDisapproveRequest: Button


        init {
            group_no = itemView.findViewById(R.id.user_group_no)
            group_admin = itemView.findViewById(R.id.user_group_admin)
            admin_phone_number = itemView.findViewById(R.id.admin_phone_number)
            btnApproveRequest = itemView.findViewById(R.id.btnApproveGroupRequest)
            btnDisapproveRequest = itemView.findViewById(R.id.btnDisapproveGroupRequest)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items_group_requests, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.group_no.text = "Group: " + request_list[position].group_number.toString()
        holder.group_admin.text = "Admin: " + request_list[position].admin_first_name + " " + request_list[position].admin_last_name
        holder.admin_phone_number.text = "Phone Number: " + request_list[position].admin_phone_number

        val request_credentials = request_list[position]

        holder.btnApproveRequest.setOnClickListener {
            onItemClick?.invoke(request_credentials)

        }
        holder.btnDisapproveRequest.setOnClickListener {
            onItemClick2?.invoke(request_credentials)

        }

    }

    override fun getItemCount(): Int {
        return request_list.size
    }
}