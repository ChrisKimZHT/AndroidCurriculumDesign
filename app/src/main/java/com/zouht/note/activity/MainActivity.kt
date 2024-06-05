package com.zouht.note.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.zouht.note.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        checkLoginStat()
    }

    private fun checkLoginStat() {
        val sharedPref = getSharedPreferences("login", MODE_PRIVATE)
        val loginStat = sharedPref.getBoolean("loginStat", false)
        if (!loginStat) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}