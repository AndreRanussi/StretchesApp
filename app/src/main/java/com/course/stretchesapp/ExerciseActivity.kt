package com.course.stretchesapp


import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.LinearLayoutManager
import com.course.stretchesapp.databinding.ActivityExerciseBinding
import com.course.stretchesapp.databinding.DialogCustomBackConfirmationBinding
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
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null


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
            customDialogForBackButton()
        }

        val mainHandler = Handler(Looper.getMainLooper())

        // adding a timer delay in order to allow for the Text to Speech engine to load.
        mainHandler.postDelayed( {
            setupRestView()
        }, 100)

        setUpExerciseStatusRecyclerView()

    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)


        dialogBinding.btnYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }

        dialogBinding.btnNo.setOnClickListener{
            customDialog.dismiss()
        }

        customDialog.show()
    }

    private fun setUpExerciseStatusRecyclerView() {
        binding?.rvExerciseStatus?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
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
            speakOut("Get Ready for ${exerciseList!![currentExercisePosition+1].getName()}")

        } else {
            binding?.tvTitle?.text = "REST"
            speakOut("The next exercise is ${exerciseList!![currentExercisePosition+1].getName()}")
        }

        if(restTimer != null ) {
            restTimer?.cancel()
            restProgress = 0
        }
        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition+1].getName()



        setRestProgressBar()

    }

    private fun setupExerciseView(){
        // start bell
        try {
            val soundURI = Uri.parse(
                "android.resource://com.course.stretchesapp/" + R.raw.boxing_bell)
            player = MediaPlayer.create(applicationContext, soundURI)
            player?.isLooping = false
            player?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }


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


        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.postDelayed( {
            setExerciseProgressBar()
        }, 1000)


    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        binding?.progressBar?.max = restPeriod

        restTimer = object: CountDownTimer((restPeriod * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = restPeriod - restProgress
                binding?.tvTimer?.text = (restPeriod - restProgress).toString()


                exerciseList!![currentExercisePosition+1].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
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
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java )
                    startActivity(intent)
                }
            }
        }.start()

    }
    override fun onInit(status: Int){
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
         tts!!.speak(text, TextToSpeech.QUEUE_ADD, null, null)
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

        if (player != null) {
            player!!.stop()
        }

        binding = null
    }

}


