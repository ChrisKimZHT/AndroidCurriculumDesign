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
import com.zouht.note.data.UserManager
import com.zouht.note.model.User
import java.util.Locale

class RegisterActivity : AppCompatActivity() {
    val userManager = UserManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
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
        findViewById<Button>(R.id.loginBtn).setOnClickListener { onLoginButtonClick() }
        findViewById<Button>(R.id.registerBtn).setOnClickListener { onRegisterButtonClick() }
    }

    private fun onLoginButtonClick() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun onRegisterButtonClick() {
        val input = acquireInput()
        val email = input["email"]
        val username = input["username"]
        val password = input["password"]
        val passwordConfirm = input["passwordConfirm"]
        if (email.isNullOrEmpty() || username.isNullOrEmpty() || password.isNullOrEmpty() || passwordConfirm.isNullOrEmpty()) {
            val message = "请填写所有字段"
            val duration = Snackbar.LENGTH_SHORT
            Snackbar.make(findViewById(R.id.main), message, duration).show()
            return
        }
        if (password != passwordConfirm) {
            val message = "密码不一致"
            val duration = Snackbar.LENGTH_SHORT
            Snackbar.make(findViewById(R.id.main), message, duration).show()
            return
        }
        if (password.length < 6) {
            val message = "密码长度至少为6"
            val duration = Snackbar.LENGTH_SHORT
            Snackbar.make(findViewById(R.id.main), message, duration).show()
            return
        }
        if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()).not()) {
            val message = "邮箱格式错误"
            val duration = Snackbar.LENGTH_SHORT
            Snackbar.make(findViewById(R.id.main), message, duration).show()
            return
        }
        val user = userManager.getUserByEmail(email.lowercase())
        if (user != null) {
            val message = "邮箱已被注册"
            val duration = Snackbar.LENGTH_SHORT
            Snackbar.make(findViewById(R.id.main), message, duration).show()
            return
        }
        userManager.insertUser(User(null, email.lowercase(), username, password))
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun acquireInput(): Map<String, String> {
        val email = findViewById<EditText>(R.id.emailInput).text.toString()
        val username = findViewById<EditText>(R.id.usernameInput).text.toString()
        val password = findViewById<EditText>(R.id.passwordInput).text.toString()
        val passwordConfirm = findViewById<EditText>(R.id.passwordConfirmInput).text.toString()
        return mapOf(
            "email" to email,
            "username" to username,
            "password" to password,
            "passwordConfirm" to passwordConfirm
        )
    }
}