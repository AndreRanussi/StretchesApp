package com.course.stretchesapp

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "workouts_history")
data class WorkoutHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val date:String = "",
    val completed:Boolean = false
)