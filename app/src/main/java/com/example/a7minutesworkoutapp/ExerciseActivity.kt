package com.example.a7minutesworkoutapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.item_exercise_status.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(),TextToSpeech.OnInitListener {
    val data=Constants.defaultExerciseList()
    private var player:MediaPlayer?=null
    private var tts:TextToSpeech?=null
    private var restPauseOffSet:Long=0
    private var exercisePauseOffSet:Long=0
    private var currentView:String="Rest View"
    private var restTimer:CountDownTimer?=null
    private var timerDuration:Long=10000
    var exerciseTimer:CountDownTimer?=null
    var exerciseDuration:Long=30000
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExercisePosition=-1
    private lateinit var exerciseStatusAdapter: ExerciseStatusAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        initRecycleView()
        addDataSet()
        tvTimer.text=(timerDuration/1000).toString()
        tts= TextToSpeech(this,this)
        player= MediaPlayer.create(this,R.raw.press_start_cut)
        setSupportActionBar(toolbar_exe_activity)
        val actionbar=supportActionBar
        if(actionbar!=null)
        {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_exe_activity.setNavigationOnClickListener {
            if(currentView=="Rest View")
            {
                pauseRest()
            }else
            {
                pauseExercise()
            }
            customDialogPicker()
        }

        exerciseList=Constants.defaultExerciseList()
        startTimer(restPauseOffSet)
    }
    fun startTimer(pauseoffsetL: Long)
    {
        restTimer=object:CountDownTimer(timerDuration-pauseoffsetL,1000){
            override fun onTick(millisUntilFinished: Long) {
                restPauseOffSet=timerDuration-millisUntilFinished
                tvTimer.text=(millisUntilFinished/1000).toString()
                progressbar.progress=(millisUntilFinished/1000).toInt()
                tv_upcoming_exercise.text=exerciseList!![currentExercisePosition+1].getName()
            }

            override fun onFinish() {
                currentExercisePosition++
                data[currentExercisePosition].setIsSelected(true)
                exerciseStatusAdapter.notifyDataSetChanged()
               // setCurrent()
                setExerciseView()
                restPauseOffSet=0
            }
        }.start()

    }
    fun initRecycleView(){
        rvExerciseStatus.apply {
            layoutManager=LinearLayoutManager(this@ExerciseActivity,LinearLayoutManager.HORIZONTAL,false)
            exerciseStatusAdapter= ExerciseStatusAdapter()
            adapter=exerciseStatusAdapter
        }
    }
    fun addDataSet(){
        exerciseStatusAdapter.submitList(data)
    }

    fun startexerciseTimer(pauseoffsetL:Long)
    {

        exerciseTimer=object:CountDownTimer(exerciseDuration-pauseoffsetL,1000){
            override fun onTick(millisUntilFinished: Long) {
                exercisePauseOffSet=exerciseDuration-millisUntilFinished
                tvexerciseTimer.text=(millisUntilFinished/1000).toString()
                exerciseprogressbar.progress=(millisUntilFinished/1000).toInt()
            }

            override fun onFinish() {
                if(currentExercisePosition<exerciseList?.size!!-1)
                {
                    data[currentExercisePosition].setIsSelected(false)
                    data[currentExercisePosition].setIsCompleted(true)
                    exerciseStatusAdapter.notifyDataSetChanged()
                    //setCurrent()
                    setupRestView()
                }else {
                    finish()
                    gotoFinish()
                }
                exercisePauseOffSet=0
            }
        }.start()
    }
    fun pauseRest()
    {
        if(restTimer!=null)
        {
            restTimer!!.cancel()
        }
    }
    fun pauseExercise()
    {
        if(exerciseTimer!=null)
        {
            exerciseTimer!!.cancel()
        }
    }
fun gotoFinish()
{
    var i=Intent(this@ExerciseActivity,FinishActivity::class.java)
    startActivity(i)
}
    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null)
            restTimer!!.cancel()
        if(exerciseTimer!=null)
            exerciseTimer!!.cancel()
        if(tts!=null)
        {
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!=null)
            player!!.stop()
    }
    fun setExerciseView()
    {
        currentView="Exercise View"
        llrestview.visibility=View.GONE
        llexersiceview.visibility=View.VISIBLE
        tv_exercisename.text=exerciseList!![currentExercisePosition].getName()
        iv_exerciseimg.setImageResource(exerciseList!![currentExercisePosition].getImage())
        speakOut(exerciseList!![currentExercisePosition].getName())
        startexerciseTimer(exercisePauseOffSet)
    }
    fun setupRestView()
    {
        currentView="Rest View"
        llrestview.visibility=View.VISIBLE
        llexersiceview.visibility=View.GONE
        if(restTimer!=null)
        {
            restTimer!!.cancel()
        }
        player!!.isLooping=false
        player!!.start()
        startTimer(restPauseOffSet)
    }
    fun speakOut(text:String)
    {
        if(text!=null)
        {
            tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
        }
        else
        {
            Toast.makeText(this, "Empty Text", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS)
        {
            val result=tts!!.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTs","Unsupported lang.")
            }
        }else
        {
            Log.e("TTs","Intialization failed")
        }
    }
    fun setCurrent()
    {
        if(exerciseList!![currentExercisePosition].getIsSelected())
        {
            tvItem.setBackgroundResource(R.drawable.current_bg)
            tvItem.setTextColor(Color.BLACK)
        }else if(exerciseList!![currentExercisePosition].getIsCompleted())
        {
            tvItem.setBackgroundResource(R.drawable.finished_bg)
            tvItem.setTextColor(Color.WHITE)
        }else
        {
            tvItem.setBackgroundResource(R.drawable.item_circuler_grey_bg)
            tvItem.setTextColor(Color.BLACK)
        }

    }
    fun customDialogPicker(){
        val customDialog=Dialog(this)
        customDialog.setContentView(R.layout.custom_dialog)
        customDialog.btn_yes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.btn_no.setOnClickListener {
            if(currentView=="Rest View")
            {
                startTimer(restPauseOffSet)
            }
            else
            {
                startexerciseTimer(exercisePauseOffSet)
            }
            customDialog.dismiss()
        }
        customDialog.show()
    }

}