package com.course.stretchesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.course.stretchesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    var restTime = 0
    var exerciseTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

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

        binding?.flStart?.setOnClickListener{
            val intent = Intent(this, ExerciseActivity::class.java)
            intent.putExtra("restTime", restTime)
            intent.putExtra("exerciseTime", exerciseTime)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}