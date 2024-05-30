package com.zouht.note.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "app.db"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE user (
                userId INTEGER PRIMARY KEY AUTOINCREMENT,
                email TEXT NOT NULL UNIQUE,
                username TEXT NOT NULL,
                password TEXT NOT NULL,
                avatar TEXT
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE note (
                noteId INTEGER PRIMARY KEY AUTOINCREMENT,
                userId INTEGER NOT NULL,
                title TEXT NOT NULL,
                content TEXT NOT NULL,
                createdTime INTEGER NOT NULL,
                FOREIGN KEY (userId) REFERENCES user(userId)
            )
            """.trimIndent()
        )
        db.execSQL(
            """
            CREATE TABLE comment (
                commentId INTEGER PRIMARY KEY AUTOINCREMENT,
                noteId INTEGER NOT NULL,
                userId INTEGER NOT NULL,
                content TEXT NOT NULL,
                createdTime INTEGER NOT NULL,
                FOREIGN KEY (noteId) REFERENCES note(noteId),
                FOREIGN KEY (userId) REFERENCES user(userId)
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS user")
        db.execSQL("DROP TABLE IF EXISTS note")
        db.execSQL("DROP TABLE IF EXISTS comment")
        onCreate(db)
    }
}