package com.example.evides.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.evides.R

const val EXTRA_CI_NUMBER = "com.example.evides.CI_NUMBER"

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editCiNumber = findViewById<EditText>(R.id.editCiNumber)
        val buttonContinue = findViewById<Button>(R.id.buttonContinue)

        buttonContinue.setOnClickListener(View.OnClickListener { view ->
            val ci = editCiNumber.text.toString().trim()
            if (ci.isBlank()) {
                Toast.makeText(this, getString(R.string.toast_ci_required),
                    Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, FolderActivity::class.java)
                intent.putExtra(EXTRA_CI_NUMBER, ci)
                startActivity(intent)
            }
        })
    }
}