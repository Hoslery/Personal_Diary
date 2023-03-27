package com.toropov.personaldiary.allentries

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toropov.personaldiary.R
import com.toropov.personaldiary.database.Entry

class EntriesAdapter(private val clickListener: OnItemClickListener): RecyclerView.Adapter<EntriesViewHolder>() {

    interface OnItemClickListener{
        fun onDeleteClicked(entry: Entry)
        fun onUpdateClicked(entry: Entry)
    }

    var data = listOf<Entry>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntriesViewHolder {
        return EntriesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: EntriesViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, clickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}