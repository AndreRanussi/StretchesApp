package com.course.stretchesapp

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "workouts_history")
data class WorkoutHistoryEntity(
    @PrimaryKey
    val date:String
)