package com.zouht.note.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.zouht.note.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initActionBar()
        initListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
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
        findViewById<Button>(R.id.loginBtn).setOnClickListener { onLoginButtonClick() }
        findViewById<Button>(R.id.registerBtn).setOnClickListener { onRegisterButtonClick() }
    }

    private fun acquireInput(): Pair<String, String> {
        val email = findViewById<EditText>(R.id.usernameInput).text.toString()
        val password = findViewById<EditText>(R.id.passwordInput).text.toString()
        return Pair(email, password)
    }

    private fun onLoginButtonClick() {
        val (email, password) = acquireInput()
        if (email.isEmpty() || password.isEmpty()) {
            Snackbar.make(
                findViewById(R.id.main),
                "请输入账号和密码",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }
    }

    private fun onRegisterButtonClick() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}