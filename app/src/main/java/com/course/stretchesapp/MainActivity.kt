package com.course.stretchesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.Toast
import com.course.stretchesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null



    private val seekBarRest = binding?.sbRestTimer
    private val seekBarValueRest = binding?.tvRestTimeShow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flStart?.setOnClickListener{
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        seekBarRest?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBarValueRest?.text = "${seekBarRest.progress * 5}"
                Toast.makeText(this@MainActivity, "$seekBarRest.progress", Toast.LENGTH_SHORT).show()
            }
        })
        }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}