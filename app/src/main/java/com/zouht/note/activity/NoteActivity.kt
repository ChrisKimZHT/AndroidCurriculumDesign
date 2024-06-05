package com.zouht.note.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_note)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initActionBar()
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
        val noteId = intent.getIntExtra("noteId", -1)
        val note = noteManager.getNoteByNoteId(noteId)
        if (note == null) {
            finish()
            return
        }
        val user = userManager.getUserById(note.userId)
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        findViewById<Toolbar>(R.id.toolbar).setTitle(note.title)
        findViewById<TextView>(R.id.content).setText(note.content)
        findViewById<TextView>(R.id.author).setText(user?.username)
        findViewById<TextView>(R.id.date).setText(sdf.format(note.createdTime))
    }
}