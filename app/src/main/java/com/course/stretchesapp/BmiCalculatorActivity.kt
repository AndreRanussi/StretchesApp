package com.course.stretchesapp

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.DeadObjectException
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.course.stretchesapp.databinding.ActivityBmiCalculatorBinding
import java.math.RoundingMode
import kotlin.math.roundToInt

class BmiCalculatorActivity : AppCompatActivity() {
    private var binding: ActivityBmiCalculatorBinding? = null


    private var height: Double? = null
    private var weight: Double? = null
    private var bmiResult: Double? = null
    private var result: String? = null
    private var resultMessage: String? = null

    private var etHeight: Editable? = null
    private var etWeight: Editable? = null

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
            finish()
        }

        binding?.btnBmi?.setOnClickListener() {
            calculateBmi()

        }

    }


    private fun calculateBmi() {
           if (etHeight!!.isNotEmpty() || etWeight!!.isNotEmpty()) {
            height = etHeight.toString().toDouble()
            weight = etWeight.toString().toDouble()
            bmiResult =
                (weight!! / (((height!! * height!!) * 100).roundToInt() / 100.0) * 100).roundToInt() / 100.0
            binding?.tvBmi?.text = bmiResult!!.toString()

            binding?.tvYourBmi?.visibility = View.VISIBLE
            binding?.tvBmi?.visibility = View.VISIBLE
            binding?.tvBmiResult?.visibility = View.VISIBLE
            binding?.tvBmiResultMessage?.visibility = View.VISIBLE
        } else {
            binding?.tvBmiResultMessage?.text = "Please enter a valid value for Weight and Height"
            binding?.tvBmiResultMessage?.visibility = View.VISIBLE
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}





