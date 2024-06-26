package com.zouht.note.data

import android.annotation.SuppressLint
import android.content.Context
import com.zouht.note.model.User

class UserManager(private val context: Context) {
    private val dbHelper = DBHelper(context)

    fun insertUser(user: User) {
        val db = dbHelper.writableDatabase
        db.execSQL(
            "INSERT INTO user (username, email, password) VALUES (?, ?, ?)",
            arrayOf(user.username, user.email, user.password)
        )
        db.close()
    }

    @SuppressLint("Range")
    fun listUsers(): List<User> {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user", null)
        val list = mutableListOf<User>()
        while (cursor.moveToNext()) {
            val user = User(
                cursor.getInt(cursor.getColumnIndex("userId")),
                cursor.getString(cursor.getColumnIndex("email")),
                cursor.getString(cursor.getColumnIndex("username")),
                cursor.getString(cursor.getColumnIndex("password")),
            )
            list.add(user)
        }
        cursor.close()
        db.close()
        return list
    }

    @SuppressLint("Range")
    fun getUserById(userId: Int): User? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user WHERE userId = ?", arrayOf(userId.toString()))
        cursor.moveToNext()
        if (cursor.count == 0) {
            cursor.close()
            db.close()
            return null
        }
        val user = User(
            cursor.getInt(cursor.getColumnIndex("userId")),
            cursor.getString(cursor.getColumnIndex("email")),
            cursor.getString(cursor.getColumnIndex("username")),
            cursor.getString(cursor.getColumnIndex("password"))
        )
        cursor.close()
        db.close()
        return user
    }

    @SuppressLint("Range")
    fun getUserByEmail(email: String): User? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user WHERE email = ?", arrayOf(email))
        cursor.moveToNext()
        if (cursor.count == 0) {
            cursor.close()
            db.close()
            return null
        }
        val user = User(
            cursor.getInt(cursor.getColumnIndex("userId")),
            cursor.getString(cursor.getColumnIndex("email")),
            cursor.getString(cursor.getColumnIndex("username")),
            cursor.getString(cursor.getColumnIndex("password"))
        )
        cursor.close()
        db.close()
        return user
    }

    fun updateUser(user: User) {
        val db = dbHelper.writableDatabase
        db.execSQL(
            "UPDATE user SET username = ?, email = ?, password = ? WHERE userId = ?",
            arrayOf(user.username, user.email, user.password, user.userId)
        )
        db.close()
    }

    fun deleteUser(userId: Int) {
        val db = dbHelper.writableDatabase
        db.execSQL("DELETE FROM user WHERE userId = ?", arrayOf(userId))
        db.close()
    }
}