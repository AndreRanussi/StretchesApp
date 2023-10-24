package com.course.stretchesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import android.widget.ToggleButton
import com.course.stretchesapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restPeriod = 3
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exercisePeriod = 5
    private var exerciseProgress = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()

    }


    private fun setupRestView(){
        binding?.flProgressBar?.visibility = VISIBLE
        binding?.flExerciseView?.visibility = INVISIBLE
        binding?.tvTitle?.text = "GET READY FOR"

        if(restTimer != null ) {
            restTimer?.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setupExerciseView(){
        binding?.flProgressBar?.visibility = INVISIBLE
        binding?.flExerciseView?.visibility = VISIBLE
        binding?.tvTitle?.text = "Exercise Name 1"

        if(exerciseTimer != null ) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        setExerciseProgressBar()
    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        binding?.progressBar?.max = restPeriod

        restTimer = object: CountDownTimer((restPeriod * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = restPeriod - restProgress
                binding?.tvTimer?.text = (restPeriod - restProgress).toString()
            }
            override fun onFinish() {
                setupExerciseView()

            }
        }.start()

    }

    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = restProgress
        binding?.progressBarExercise?.max = exercisePeriod

        exerciseTimer = object: CountDownTimer((exercisePeriod * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = exercisePeriod - exerciseProgress
                binding?.tvTimerExercise?.text = (exercisePeriod - exerciseProgress).toString()
            }
            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Well done! REST...", Toast.LENGTH_SHORT).show()
                setupRestView()
            }
        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer != null ) {
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null ) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding = null
    }

}