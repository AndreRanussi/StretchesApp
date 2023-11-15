package com.course.stretchesapp

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert
    suspend fun insert(historyEntity: WorkoutHistoryEntity)


    @Update
    suspend fun update(historyEntity: WorkoutHistoryEntity)


    @Delete
    suspend fun delete(historyEntity: WorkoutHistoryEntity)


    @Query("SELECT * FROM `WORKOUTS-HISTORY`")
    fun fetchAllHistory():Flow<List<WorkoutHistoryEntity>>

}