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
    private var fieldCheckerFlag = false

    private var etHeight = binding?.etHeight?.text.toString()
    private var etWeight = binding?.etWeight?.text.toString()

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

        binding?.btnBmi?.setOnClickListener {
            fieldCheckerFlag = checkFields()
            calculateBmi()

        }

    }



    private fun calculateBmi() {

        if (fieldCheckerFlag) {

            // 1.Implement BMI Calculation and display result
            height = etHeight.toDouble()
            weight = etWeight.toDouble()
            bmiResult =
                (weight!! / (((height!! * height!!) * 100).roundToInt() / 100.0) * 100).roundToInt() / 100.0
            binding?.tvBmi?.text = bmiResult!!.toString()


            // 2. Conditional for the BMI category and display it

           result = when {
                bmiResult!! < 18.5 -> "Category: Underweight"
                bmiResult!! in 18.5..24.9 -> "Category: Healthy"
                bmiResult!! in 25.0..29.9 -> "Category: Overweight"
                bmiResult!! in 30.0..39.9 -> "Category: Obese"
                bmiResult!! <= 40.0 -> "Category: Severely Obese"
               else -> "please try again!"
           }
            binding?.tvBmiResult?.text = result

            // 3. Conditional for BMI message and display it

            binding?.tvBmiResultMessage?.text =
                when (result) {
                    "Category: Underweight" -> "Oops! You really need to take better care of yourself! Eat more!"
                    "Category: Healthy" -> "Congratulations! You are in a good shape!"
                    "Category: Overweight" -> "Oops! You need to take care of your yourself! Workout maybe!"
                    "Category: Obese" -> "Oops! You really need to take care of your yourself! Workout maybe!"
                    "Category: Severely Obese" ->  "OMG! You are in a very dangerous condition! Act now!"
                    else -> " "

                }

            binding?.tvYourBmi?.visibility = View.VISIBLE
            binding?.tvBmi?.visibility = View.VISIBLE
            binding?.tvBmiResult?.visibility = View.VISIBLE
            binding?.tvBmiResultMessage?.visibility = View.VISIBLE
        } else {
            binding?.tvBmiResultMessage?.text = "Please enter a valid value for Weight and Height"
            binding?.tvBmiResultMessage?.visibility = View.VISIBLE

        }
    }


    private fun checkFields(): Boolean {
        etHeight = binding?.etHeight?.text.toString()
        etWeight = binding?.etWeight?.text.toString()

        if(etWeight.isEmpty()) return false
        if(etHeight.isEmpty()) return false
    return true
    }


    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }

}





