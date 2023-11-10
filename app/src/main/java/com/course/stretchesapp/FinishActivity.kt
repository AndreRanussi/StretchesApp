package com.course.stretchesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.course.stretchesapp.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.btnFinish?.setOnClickListener{
            finish()
        }


    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}