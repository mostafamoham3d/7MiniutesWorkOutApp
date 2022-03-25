package com.example.a7minutesworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusAdapter :RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
private var exerciseList:ArrayList<ExerciseModel>?=null
    class exerciseAdapter constructor(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val tvitem=itemView.tvItem
        fun bind(exerciseModel: ExerciseModel){
            tvitem.setText(exerciseModel.getId().toString())
            tvitem.setTextColor(Color.WHITE)
            tvitem.setBackgroundResource(R.drawable.item_circuler_grey_bg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return exerciseAdapter(LayoutInflater.from(parent.context).inflate(R.layout.item_exercise_status,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder)
        {
            is exerciseAdapter->{
                holder.bind(exerciseList!![position])
                when {
                    exerciseList!![position].getIsSelected() -> {
                        holder.tvitem.setBackgroundResource(R.drawable.current_bg)
                        holder.tvitem.setTextColor(Color.BLACK)
                    }
                    exerciseList!![position].getIsCompleted() -> {
                        holder.tvitem.setBackgroundResource(R.drawable.finished_bg)
                        holder.tvitem.setTextColor(Color.WHITE)
                    }
                    else -> {
                        holder.tvitem.setBackgroundResource(R.drawable.item_circuler_grey_bg)
                        holder.tvitem.setTextColor(Color.BLACK)
                    }
                }
            }
        }
    }

    fun submitList(exerciseList:ArrayList<ExerciseModel>)
    {
        this.exerciseList=exerciseList
    }

    override fun getItemCount(): Int {
        return exerciseList!!.size
    }
}
