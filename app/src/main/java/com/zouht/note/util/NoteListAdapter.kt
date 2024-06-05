package com.zouht.note.util

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zouht.note.R
import com.zouht.note.activity.NoteActivity
import com.zouht.note.model.Note
import java.text.SimpleDateFormat
import java.util.Locale

class NoteListAdapter(private val notes: List<Note>) :
    RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteTitle: TextView = view.findViewById(R.id.noteTitle)
        val noteTime: TextView = view.findViewById(R.id.noteTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val note = notes[position]
            val intent = Intent(viewHolder.itemView.context, NoteActivity::class.java)
            intent.putExtra("noteId", note.noteId)
            viewHolder.itemView.context.startActivity(intent)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
        holder.noteTitle.text = notes[position].title
        holder.noteTime.text = sdf.format(notes[position].createdTime)
    }

    override fun getItemCount() = notes.size
}