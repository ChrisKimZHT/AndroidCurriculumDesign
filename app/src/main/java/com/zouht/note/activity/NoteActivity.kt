package com.zouht.note.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zouht.note.R
import com.zouht.note.data.NoteManager
import com.zouht.note.data.UserManager
import java.text.SimpleDateFormat
import java.util.Locale

class NoteActivity : AppCompatActivity() {
    private val noteManager = NoteManager(this)
    private val userManager = UserManager(this)
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        noteId = intent.getIntExtra("noteId", -1)
        initActionBar()
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        initNote()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initActionBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun initNote() {
        val note = noteManager.getNoteByNoteId(noteId)
        val userId = getSharedPreferences("login", MODE_PRIVATE).getInt("userId", -1)
        if (note == null) {
            finish()
            return
        }
        val user = userManager.getUserById(note.userId)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        findViewById<Toolbar>(R.id.toolbar).title = "笔记详情：${note.title}"
        findViewById<TextView>(R.id.content).text = note.content
        findViewById<TextView>(R.id.author).text = user?.username
        findViewById<TextView>(R.id.date).text = sdf.format(note.createdTime)
        if (userId != note.userId) {
            findViewById<Button>(R.id.editBtn).visibility = Button.GONE
            findViewById<Button>(R.id.deleteBtn).visibility = Button.GONE
        }
    }

    private fun initListeners() {
        findViewById<Button>(R.id.editBtn).setOnClickListener { onEditBtnClick() }
        findViewById<Button>(R.id.deleteBtn).setOnClickListener { onDeleteBtnClick() }
        findViewById<Button>(R.id.commentBtn).setOnClickListener { onCommentBtnClick() }
    }

    private fun onEditBtnClick() {
        val intent = android.content.Intent(this, EditActivity::class.java)
        intent.putExtra("noteId", noteId)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun onDeleteBtnClick() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("删除")
        builder.setMessage("确定要删除这篇笔记吗？")
        builder.setPositiveButton("确认") { _, _ ->
            noteManager.deleteNote(noteId)
            finish()
        }
        builder.setNegativeButton("取消") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun onCommentBtnClick() {
        val intent = Intent(this, CommentActivity::class.java)
        intent.putExtra("noteId", noteId)
        startActivity(intent)
    }
}