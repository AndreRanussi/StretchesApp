package com.course.stretchesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BmiCalculatorActivity : AppCompatActivity() {
    private val binding: BmiCalculatorActivity = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BmiCalculatorActivity(i)
        setContentView(R.layout.activity_bmi_calculator)
    }
}