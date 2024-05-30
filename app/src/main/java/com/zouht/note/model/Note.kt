package com.zouht.note.model

data class Note(
    val noteId: Int,
    val userId: Int,
    val title: String,
    val content: String,
    val createdTime: Long
)
