package com.zouht.note.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import java.text.SimpleDateFormat
import java.util.Locale

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
        initNoteList()
    }

    override fun onStart() {
        super.onStart()
        checkLoginStat()
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
    }

    private fun initNoteList() {
//        val notes = noteManager.listNotes()
        val notes = ArrayList<Note>()
        for (i in 1..100) {
            notes.add(Note(i, 1, "test$i", "content", 0))
        }
        val noteList = findViewById<RecyclerView>(R.id.noteList)
        noteList.layoutManager = LinearLayoutManager(this)
        noteList.adapter = NoteListAdapter(notes)
    }

    private fun onLogoutBtnClick() {
        val sharedPref = getSharedPreferences("login", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}

class NoteListAdapter(private val notes: List<Note>) :
    RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val noteTitle: TextView = view.findViewById(R.id.noteTitle)
        val noteTime: TextView = view.findViewById(R.id.noteTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        holder.noteTitle.text = notes[position].title
        holder.noteTime.text = sdf.format(notes[position].createdTime)
    }

    override fun getItemCount() = notes.size
}