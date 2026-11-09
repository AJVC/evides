package com.example.evides.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.evides.R

class FolderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_folder)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ci = intent.getStringExtra(EXTRA_CI_NUMBER) ?: ""

        val textFolderName = findViewById<TextView>(R.id.textFolderName)
        val buttonBack = findViewById<ImageButton>(R.id.buttonBack)
        val buttonManage = findViewById<ImageButton>(R.id.buttonManage)
        val buttonAdd = findViewById<View>(R.id.buttonAdd)

        textFolderName.text = "CI-" + ci

        buttonBack.setOnClickListener(View.OnClickListener { view ->
            finish()
        })

        buttonManage.setOnClickListener(View.OnClickListener { view ->
            val intent = Intent(this, EvidenceCrudActivity::class.java)
            intent.putExtra(EXTRA_CI_NUMBER, ci)
            startActivity(intent)
        })

        buttonAdd.setOnClickListener(View.OnClickListener { view ->
            Toast.makeText(this, getString(R.string.folder_add_soon),
                Toast.LENGTH_LONG).show()
        })
    }
}