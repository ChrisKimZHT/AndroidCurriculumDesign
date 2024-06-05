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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zouht.note.R
import com.zouht.note.data.CommentManager
import com.zouht.note.data.UserManager
import com.zouht.note.model.Comment
import com.zouht.note.util.CommentListAdapter

class CommentActivity : AppCompatActivity() {
    private val commentManager = CommentManager(this)
    private val userManager = UserManager(this)
    private var noteId = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comment)
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
        initCommentList()
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

    private fun initListeners() {
        findViewById<Button>(R.id.commentBtn).setOnClickListener { onCommentBtnClick() }
    }

    private fun initCommentList() {
        if (noteId == -1) {
            finish()
            return
        }
        val comments = commentManager.listComments(noteId)
        for (comment in comments) {
            val user = userManager.getUserById(comment.userId)
            if (user != null) {
                comment.username = user.username
            }
        }
        val sortedComments = comments.sortedByDescending { it.createdTime }
        val commentList = findViewById<RecyclerView>(R.id.commentList)
        commentList.layoutManager = LinearLayoutManager(this)
        commentList.adapter = CommentListAdapter(sortedComments)
    }

    private fun onCommentBtnClick() {
        val comment = findViewById<EditText>(R.id.commentInput).text.toString()
        val userId = getSharedPreferences("login", MODE_PRIVATE).getInt("userId", -1)
        if (comment.isEmpty()) {
            Snackbar.make(findViewById(R.id.main), "评论不可为空", Snackbar.LENGTH_SHORT).show()
            return
        }
        commentManager
            .insertComment(Comment(-1, noteId, userId, comment, System.currentTimeMillis()))
        initCommentList()
        findViewById<EditText>(R.id.commentInput).text.clear()
    }
}