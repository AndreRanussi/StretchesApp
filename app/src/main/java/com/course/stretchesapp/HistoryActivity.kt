package com.course.stretchesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.course.stretchesapp.databinding.ActivityBmiCalculatorBinding
import com.course.stretchesapp.databinding.ActivityHistoryBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private var binding: ActivityHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarHistoryActivity)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        val dao =(application as WorkoutApp).db.historyDao()
        getAllCompletedDates(dao)
    }


    private fun getAllCompletedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect() {
                allCompletedDatesList ->
                if(allCompletedDatesList.isNotEmpty()){
                    binding?.tvTitle?.visibility = View.VISIBLE
                    binding?.rvHistoryView?.visibility = View.VISIBLE
                    binding?.tvWarning?.visibility = View.GONE

                    binding?.rvHistoryView?.layoutManager = LinearLayoutManager(this@HistoryActivity)

                    val dates = arrayListOf<String>()
                    for (date in allCompletedDatesList) {
                        dates.add(date.date)
                    }

                    val historyAdapter = HistoryAdapter(dates)
                    binding?.rvHistoryView?.adapter = historyAdapter


                } else {
                    binding?.tvTitle?.visibility = View.GONE
                    binding?.rvHistoryView?.visibility = View.GONE
                    binding?.tvWarning?.visibility = View.VISIBLE
                }

            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        binding =  null
    }
}
