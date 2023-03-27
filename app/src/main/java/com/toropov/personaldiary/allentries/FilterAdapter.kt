package com.toropov.personaldiary.allentries

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toropov.personaldiary.database.Entry

class FilterAdapter(private val clickListener: OnFilterItemClickListener): RecyclerView.Adapter<FilterViewHolder>() {

    interface OnFilterItemClickListener{
        fun onFilterClicked(tag: String, selected: Boolean)
    }

    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}