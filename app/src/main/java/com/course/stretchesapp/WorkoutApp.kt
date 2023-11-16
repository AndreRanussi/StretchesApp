package com.course.stretchesapp

import android.app.Application

class WorkoutApp: Application() {

    val db by lazy {
        WorkoutDatabase.getInstance(this)
    }

}