package com.sambaxfinance.sambax.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sambaxfinance.sambax.R

class MyAdapterUserGroups(val context: Context, val group_list: List<GeneralGroupLandingResponseModel>):RecyclerView.Adapter<MyAdapterUserGroups.ViewHolder>()  {

    var onItemClick:((GeneralGroupLandingResponseModel) -> Unit)? = null

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var group_id: TextView
        var btnGoToGroup: Button




        init {
            group_id = itemView.findViewById(R.id.user_group_id)
            btnGoToGroup = itemView.findViewById(R.id.btnGoToGroup)



        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items_user_groups, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.group_id.text = "Group: " + group_list[position].id.toString()

        val group_credentials = group_list[position]
        /*holder.itemView.setOnClickListener {
            onItemClick?.invoke(group_credentials)

        }*/
        holder.btnGoToGroup.setOnClickListener {
            onItemClick?.invoke(group_credentials)

        }


    }

    override fun getItemCount(): Int {
        return group_list.size
    }
}