package com.zouht.note.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zouht.note.R
import com.zouht.note.data.NoteManager
import com.zouht.note.model.Note
import com.zouht.note.util.NoteListAdapter

class MainActivity : AppCompatActivity() {
    private val noteManager = NoteManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initListeners()
    }

    override fun onStart() {
        super.onStart()
        checkLoginStat()
        initNoteList()
    }

    private fun checkLoginStat() {
        val sharedPref = getSharedPreferences("login", MODE_PRIVATE)
        val loginStat = sharedPref.getBoolean("stat", false)
        if (!loginStat) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            return
        }
        val username = sharedPref.getString("username", "")
        val currentLoginUserText = findViewById<TextView>(R.id.currentLoginUser)
        currentLoginUserText.text = username
        return
    }

    private fun initListeners() {
        findViewById<Button>(R.id.logoutBtn).setOnClickListener { onLogoutBtnClick() }
        findViewById<Button>(R.id.createBtn).setOnClickListener { onCreateNoteBtnClick() }
        findViewById<ImageButton>(R.id.searchBtn).setOnClickListener { onSearchBtnClick() }
    }

    private fun initNoteList(notes: List<Note> = noteManager.listNotes()) {
        val sortedNotes = notes.sortedByDescending { it.createdTime }
        val noteList = findViewById<RecyclerView>(R.id.noteList)
        noteList.layoutManager = LinearLayoutManager(this)
        noteList.adapter = NoteListAdapter(sortedNotes)
    }

    private fun onLogoutBtnClick() {
        val sharedPref = getSharedPreferences("login", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun onCreateNoteBtnClick() {
        val intent = Intent(this, EditActivity::class.java)
        startActivity(intent)
    }

    private fun onSearchBtnClick() {
        val searchText = findViewById<TextView>(R.id.searchEditText).text.toString()
        val notes: List<Note> = noteManager.searchNoteByTitle(searchText)
        initNoteList(notes)
    }
}
