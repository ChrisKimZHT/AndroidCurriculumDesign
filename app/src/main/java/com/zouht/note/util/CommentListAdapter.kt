package com.zouht.note.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zouht.note.model.Comment
import java.text.SimpleDateFormat
import java.util.Locale
import com.zouht.note.R

class CommentListAdapter(private val comments: List<Comment>) :
    RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val commentUser: TextView = view.findViewById(R.id.username)
        val commentContent: TextView = view.findViewById(R.id.content)
        val commentTime: TextView = view.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.comment_list_item, parent, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
        holder.commentUser.text = comments[position].username
        holder.commentContent.text = comments[position].content
        holder.commentTime.text = sdf.format(comments[position].createdTime)
    }

    override fun getItemCount() = comments.size
}