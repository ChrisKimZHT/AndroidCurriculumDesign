package com.zouht.note.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zouht.note.R
import com.zouht.note.data.NoteManager
import com.zouht.note.model.Note

class EditActivity : AppCompatActivity() {
    private var noteId = -1;
    private val noteManager = NoteManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initActionBar()
        initListeners()
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
        noteId = intent.getIntExtra("noteId", -1)
        if (noteId == -1) {
            findViewById<Toolbar>(R.id.toolbar).title = "新建笔记"
            findViewById<Button>(R.id.save).text = "新建"
        } else {
            findViewById<Toolbar>(R.id.toolbar).title = "编辑笔记"
            findViewById<Button>(R.id.save).text = "保存"
            val note = noteManager.getNoteByNoteId(noteId)
            if (note == null) {
                finish()
                return
            }
            findViewById<EditText>(R.id.title).setText(note.title)
            findViewById<EditText>(R.id.content).setText(note.content)
        }
    }

    private fun initListeners() {
        findViewById<Button>(R.id.save).setOnClickListener { onSaveBtnClick() }
    }

    private fun onSaveBtnClick() {
        val title = findViewById<EditText>(R.id.title).text.toString()
        val content = findViewById<EditText>(R.id.content).text.toString()
        val userId = getSharedPreferences("login", MODE_PRIVATE).getInt("userId", -1)
        val createdTime = System.currentTimeMillis()
        val note = Note(noteId, userId, title, content, createdTime)
        if (noteId == -1) {
            noteManager.insertNote(note)
        } else {
            noteManager.updateNote(note)
        }
        finish()
    }
}