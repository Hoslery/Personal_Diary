package com.toropov.personaldiary.allentries

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toropov.personaldiary.R
import com.toropov.personaldiary.database.Entry

class EntriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val entryTextView: TextView = itemView.findViewById(R.id.entry_text)
    private val entryDateView: TextView = itemView.findViewById(R.id.entry_date)
    private val entryTagsView: TextView = itemView.findViewById(R.id.entry_tags)
    private val entryDeleteImageView: ImageView = itemView.findViewById(R.id.icon_delete)
    private val entryButtonUpdate: Button = itemView.findViewById(R.id.button_update)

    companion object {
        fun from(parent: ViewGroup): EntriesViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.entry_list_item, parent, false)
            return EntriesViewHolder(view)
        }
    }

    fun bind(item: Entry, clickListener: EntriesAdapter.OnItemClickListener){
        entryTextView.text = item.entryText
        entryDateView.text = item.entryDate
        if (item.entryTags == null){
            entryTagsView.text = "Тегов - нет"
        } else {
            entryTagsView.text = item.entryTags
        }

        entryDeleteImageView.setOnClickListener {
            clickListener.onDeleteClicked(item)
        }

        entryButtonUpdate.setOnClickListener {
            clickListener.onUpdateClicked(item)
        }
    }

}
