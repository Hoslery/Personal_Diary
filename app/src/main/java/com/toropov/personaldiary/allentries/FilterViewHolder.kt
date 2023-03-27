package com.toropov.personaldiary.allentries

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toropov.personaldiary.R

class FilterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val filterTextView: TextView = itemView.findViewById(R.id.filterNameTextView)
    private var isSelected: Boolean = false

    companion object {
        fun from(parent: ViewGroup): FilterViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.filter_list_item, parent, false)
            return FilterViewHolder(view)
        }
    }

    fun bind(item: String, clickListener: FilterAdapter.OnFilterItemClickListener){
        filterTextView.text = item

        itemView.setOnClickListener {
            isSelected = if (!isSelected){
                filterTextView.setBackgroundColor(Color.MAGENTA)
                true
            } else {
                filterTextView.setBackgroundColor(Color.parseColor("#FFBB86FC"))
                false
            }
            clickListener.onFilterClicked(item, isSelected)
        }

    }
}