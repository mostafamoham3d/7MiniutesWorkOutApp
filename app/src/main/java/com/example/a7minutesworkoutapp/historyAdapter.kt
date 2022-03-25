package com.example.a7minutesworkoutapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_history_raw.view.*

class historyAdapter(val context: Context , val items:ArrayList<String>):
    RecyclerView.Adapter<historyAdapter.ViewHolder>() {

    class ViewHolder(view:View):RecyclerView.ViewHolder(view)
    {
            val llhistorymainitem=view.llhistory_item_main
        val tvitem=view.tvitem
        val tvpostioton=view.tvposition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history_raw,parent,false))
    }



    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date=items.get(position)
        holder.tvpostioton.text=(position+1).toString()
        holder.tvitem.text=date
        if(position%2==0){
            holder.llhistorymainitem.setBackgroundColor(Color.parseColor("EBEBEB"))
        }else
        {
            holder.llhistorymainitem.setBackgroundColor(Color.parseColor("FFFFFF"))
        }
    }
}