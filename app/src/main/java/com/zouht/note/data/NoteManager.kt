package com.zouht.note.data

import android.annotation.SuppressLint
import android.content.Context
import com.zouht.note.model.Note

class NoteManager(private val context: Context) {
    private val dbHelper = DBHelper(context)

    fun insertNote(note: Note) {
        val db = dbHelper.writableDatabase
        db.execSQL(
            "INSERT INTO note (userId, title, content, createdTime) VALUES (?, ?, ?, ?)",
            arrayOf(note.userId, note.title, note.content, note.createdTime)
        )
        db.close()
    }

    @SuppressLint("Range")
    fun listNotes(): List<Note> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM note", null)
        val list = mutableListOf<Note>()
        while (cursor.moveToNext()) {
            val note = Note(
                cursor.getInt(cursor.getColumnIndex("noteId")),
                cursor.getInt(cursor.getColumnIndex("userId")),
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getString(cursor.getColumnIndex("content")),
                cursor.getLong(cursor.getColumnIndex("createdTime"))
            )
            list.add(note)
        }
        cursor.close()
        db.close()
        return list
    }

    @SuppressLint("Range")
    fun getNoteByNoteId(noteId: Int): Note {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM note WHERE noteId = ?", arrayOf(noteId.toString()))
        cursor.moveToNext()
        val note = Note(
            cursor.getInt(cursor.getColumnIndex("noteId")),
            cursor.getInt(cursor.getColumnIndex("userId")),
            cursor.getString(cursor.getColumnIndex("title")),
            cursor.getString(cursor.getColumnIndex("content")),
            cursor.getLong(cursor.getColumnIndex("createdTime"))
        )
        cursor.close()
        db.close()
        return note
    }

    @SuppressLint("Range")
    fun getNotesByUserId(userId: Int): List<Note> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM note WHERE userId = ?", arrayOf(userId.toString()))
        val list = mutableListOf<Note>()
        while (cursor.moveToNext()) {
            val note = Note(
                cursor.getInt(cursor.getColumnIndex("noteId")),
                cursor.getInt(cursor.getColumnIndex("userId")),
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getString(cursor.getColumnIndex("content")),
                cursor.getLong(cursor.getColumnIndex("createdTime"))
            )
            list.add(note)
        }
        cursor.close()
        db.close()
        return list
    }

    fun updateNote(note: Note) {
        val db = dbHelper.writableDatabase
        db.execSQL(
            "UPDATE note SET userId = ?, title = ?, content = ?, createdTime = ? WHERE noteId = ?",
            arrayOf(note.userId, note.title, note.content, note.createdTime, note.noteId)
        )
        db.close()
    }

    fun deleteNote(noteId: Int) {
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM note WHERE noteId = ?", arrayOf(noteId))
        db.close()
    }
}