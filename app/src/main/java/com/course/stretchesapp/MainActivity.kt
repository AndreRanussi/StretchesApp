package com.course.stretchesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.Toast
import com.course.stretchesapp.databinding.ActivityMainBinding
import java.util.Objects

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var restTime = 10
    private var exerciseTime = 30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flStart?.setOnClickListener{
            val intent = Intent(this, ExerciseActivity::class.java)
            intent.putExtra("testTime", restTime)
            intent.putExtra("exerciseTime", exerciseTime)
            startActivity(intent)
        }





        binding?.sbRestTimer?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding?.tvRestTimeShow?.text = "${binding?.sbRestTimer?.progress?.times(5)}"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                restTime = binding!!.sbRestTimer.progress.times(5)
            }
        })

        binding?.sbExerciseTimer?.setOnSeekBarChangeListener(object :
        SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding?.tvExerciseTimeShow?.text = "${binding?.sbExerciseTimer?.progress?.times(5)}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                exerciseTime = binding!!.sbExerciseTimer.progress.times(5)
            }


        })



    }




    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}