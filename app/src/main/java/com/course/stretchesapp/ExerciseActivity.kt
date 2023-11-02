package com.course.stretchesapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import com.course.stretchesapp.databinding.ActivityExerciseBinding
import java.util.Locale


class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restPeriod = 0
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exercisePeriod = 0
    private var exerciseProgress = 0

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

        restPeriod = intent.getIntExtra("restTime", 15)
        exercisePeriod = intent.getIntExtra("exerciseTime", 30)

        tts = TextToSpeech(this, this)

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()

    }


    private fun setupRestView(){
        // removing rest view from the UI
        binding?.flRestView?.visibility = VISIBLE
        binding?.tvTitle?.visibility = VISIBLE

        // adding the exercise view to the UI
        binding?.flExerciseView?.visibility = INVISIBLE
        binding?.tvExerciseName?.visibility = INVISIBLE
        binding?.ivImage?.visibility = INVISIBLE

        //Rest views upcoming
        binding?.tvUpcomingLabel?.visibility = VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = VISIBLE

        binding?.flExerciseView?.visibility = INVISIBLE

        if (currentExercisePosition+1 <= 0) {
            binding?.tvTitle?.text = "GET READY"
            speakOut("Get Ready for ${exerciseList!![currentExercisePosition+1].getName()} in $restPeriod seconds")
            Toast.makeText(this , "Get Ready for ${exerciseList!![currentExercisePosition+1].getName()} in $restPeriod seconds", Toast.LENGTH_LONG).show()
        } else {
            binding?.tvTitle?.text = "REST"
            speakOut("The next exercise is ${exerciseList!![currentExercisePosition+1].getName()} in $restPeriod seconds")
        }

        if(restTimer != null ) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition+1].getName()


        setRestProgressBar()
    }

    private fun setupExerciseView(){
        // removing rest view from the UI
        binding?.flRestView?.visibility = INVISIBLE
        binding?.tvTitle?.visibility = INVISIBLE

        // adding the exercise view to the UI
        binding?.flExerciseView?.visibility = VISIBLE
        binding?.tvExerciseName?.visibility = VISIBLE
        binding?.ivImage?.visibility = VISIBLE

        //Rest views upcoming
        binding?.tvUpcomingLabel?.visibility = INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = INVISIBLE


        if(exerciseTimer != null ) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding?.ivImage?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()
        setExerciseProgressBar()
    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        binding?.progressBar?.max = restPeriod

        restTimer = object: CountDownTimer((restPeriod * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = restPeriod - restProgress
                binding?.tvTimer?.text = (restPeriod - restProgress).toString()
            }
            override fun onFinish() {
                currentExercisePosition++
                setupExerciseView()
            }
        }.start()

    }

    private fun setExerciseProgressBar() {
        binding?.progressBarExercise?.progress = restProgress
        binding?.progressBarExercise?.max = exercisePeriod

        exerciseTimer = object: CountDownTimer((exercisePeriod * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = exercisePeriod - exerciseProgress
                binding?.tvTimerExercise?.text = (exercisePeriod - exerciseProgress).toString()
            }
            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! -1 ) {
                    setupRestView()
                }
            }
        }.start()

    }
    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS) {
             val result = tts?.setLanguage(Locale.UK)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported.")
            }
        } else {
            Log.e("TTS", "Initialisation Failed")
        }
    }

     private fun speakOut(text: String) {
         tts!!.speak(text, TextToSpeech.QUEUE_ADD, null, "")
     }



    override fun onDestroy() {
        super.onDestroy()

        if(restTimer != null ) {
            restTimer?.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null ) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        binding = null
    }

}


