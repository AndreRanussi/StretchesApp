package com.course.stretchesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.course.stretchesapp.databinding.ActivityBmiCalculatorBinding

class BmiCalculatorActivity : AppCompatActivity() {
    private var binding: ActivityBmiCalculatorBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiCalculatorBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarFinishActivity)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        supportActionBar!!.title = "BMI Calculator"

        binding?.toolbarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

    }

}