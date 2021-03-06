package com.example.a7minutesworkoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar_history_activity)
        val actionbar=supportActionBar
        if(actionbar!=null)
        {
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title="History"
        }
        toolbar_history_activity.setNavigationOnClickListener {
            onBackPressed()
        }
        getAllcompletedDates()
    }
    fun getAllcompletedDates()
    {
        val dpHandler=sqliteOpenHelper(this,null)
       val completedDatesList= dpHandler.getAllCompletedDates()
        if(completedDatesList.size>0)
        {
            tvHistory.visibility= View.VISIBLE
            rvHistory.visibility=View.VISIBLE
            tvNoDataAvailable.visibility=View.GONE
            rvHistory.layoutManager=LinearLayoutManager(this)
            val historyAdapter=historyAdapter(this,completedDatesList)
            rvHistory.adapter=historyAdapter
        }else
        {
            tvHistory.visibility= View.GONE
            rvHistory.visibility=View.GONE
            tvNoDataAvailable.visibility=View.VISIBLE
        }
    }
}