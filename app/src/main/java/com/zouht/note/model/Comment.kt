package com.zouht.note.model

data class Comment(
    val commentId: Int,
    val noteId: Int,
    val userId: Int,
    val content: String,
    val createdTime: Long,
    var username: String = ""
)
