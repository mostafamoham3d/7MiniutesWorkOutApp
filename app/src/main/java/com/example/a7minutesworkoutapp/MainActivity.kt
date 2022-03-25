package com.example.a7minutesworkoutapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llstart.setOnClickListener {
            val intent=Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
            }
        llBmistart.setOnClickListener {
            startActivity(Intent(this,BmiActivity::class.java))
        }
        llHistorystart.setOnClickListener {
            startActivity(Intent(this,HistoryActivity::class.java))
        }
    }
}