package com.course.stretchesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.lifecycleScope
import com.course.stretchesapp.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishActivity : AppCompatActivity() {
    private var binding: ActivityFinishBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val history = (application as WorkoutApp).db.historyDao()
        addDateToDatabase(history)

        binding?.btnFinish?.setOnClickListener{
            finish()
        }


    }


    private fun addDateToDatabase (historyDao: HistoryDao) {

        val c = Calendar.getInstance()
        val dateTime = c.time
        val sdf = SimpleDateFormat("dd/MM/yyyy@HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        lifecycleScope.launch {
            historyDao.insert(WorkoutHistoryEntity(date))
            Log.e("date", "Added $date")
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}