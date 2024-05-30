package com.zouht.note.data

import android.content.Context
import com.zouht.note.model.Comment

class CommentManager(private val context: Context) {
    private val dbHelper = DBHelper(context)

    fun insertComment(comment: Comment) {
        val db = dbHelper.writableDatabase
        db.execSQL(
            "INSERT INTO comment (noteId, userId, content, createdTime) VALUES (?, ?, ?, ?)",
            arrayOf(comment.noteId, comment.userId, comment.content, comment.createdTime)
        )
        db.close()
    }

    fun listComments(noteId: Int): List<Comment> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM comment WHERE noteId = ?", arrayOf(noteId.toString()))
        val list = mutableListOf<Comment>()
        while (cursor.moveToNext()) {
            val comment = Comment(
                cursor.getInt(cursor.getColumnIndex("commentId")),
                cursor.getInt(cursor.getColumnIndex("noteId")),
                cursor.getInt(cursor.getColumnIndex("userId")),
                cursor.getString(cursor.getColumnIndex("content")),
                cursor.getLong(cursor.getColumnIndex("createdTime"))
            )
            list.add(comment)
        }
        cursor.close()
        db.close()
        return list
    }

    fun getCommentByCommentId(commentId: Int): Comment {
        val db = dbHelper.readableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM comment WHERE commentId = ?", arrayOf(commentId.toString()))
        cursor.moveToNext()
        val comment = Comment(
            cursor.getInt(cursor.getColumnIndex("commentId")),
            cursor.getInt(cursor.getColumnIndex("noteId")),
            cursor.getInt(cursor.getColumnIndex("userId")),
            cursor.getString(cursor.getColumnIndex("content")),
            cursor.getLong(cursor.getColumnIndex("createdTime"))
        )
        cursor.close()
        db.close()
        return comment
    }

    fun getCommentsByUserId(userId: Int): List<Comment> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM comment WHERE userId = ?", arrayOf(userId.toString()))
        val list = mutableListOf<Comment>()
        while (cursor.moveToNext()) {
            val comment = Comment(
                cursor.getInt(cursor.getColumnIndex("commentId")),
                cursor.getInt(cursor.getColumnIndex("noteId")),
                cursor.getInt(cursor.getColumnIndex("userId")),
                cursor.getString(cursor.getColumnIndex("content")),
                cursor.getLong(cursor.getColumnIndex("createdTime"))
            )
            list.add(comment)
        }
        cursor.close()
        db.close()
        return list
    }

    fun getCommentsByNoteId(noteId: Int): List<Comment> {
        val db = dbHelper.readableDatabase
        val cursor =
            db.rawQuery("SELECT * FROM comment WHERE noteId = ?", arrayOf(noteId.toString()))
        val list = mutableListOf<Comment>()
        while (cursor.moveToNext()) {
            val comment = Comment(
                cursor.getInt(cursor.getColumnIndex("commentId")),
                cursor.getInt(cursor.getColumnIndex("noteId")),
                cursor.getInt(cursor.getColumnIndex("userId")),
                cursor.getString(cursor.getColumnIndex("content")),
                cursor.getLong(cursor.getColumnIndex("createdTime"))
            )
            list.add(comment)
        }
        cursor.close()
        db.close()
        return list
    }

    fun updateComment(comment: Comment) {
        val db = dbHelper.writableDatabase
        db.execSQL(
            "UPDATE comment SET content = ?, createdTime = ? WHERE commentId = ?",
            arrayOf(comment.content, comment.createdTime, comment.commentId)
        )
        db.close()
    }

    fun deleteComment(commentId: Int) {
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM comment WHERE commentId = ?", arrayOf(commentId))
        db.close()
    }
}