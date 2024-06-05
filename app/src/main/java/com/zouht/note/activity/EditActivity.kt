package com.zouht.note.activity

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zouht.note.R

class EditActivity : AppCompatActivity() {
    private var noteId = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initNoteId()
    }

    private fun initNoteId() {
        noteId = intent.getIntExtra("noteId", -1)
        if (noteId == -1) {
            findViewById<Toolbar>(R.id.toolbar).title = "新建笔记"
            findViewById<Button>(R.id.save).text = "新建"
        }
    }


}