package com.pichurchyk.nutrition.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pichurchyk.nutrition.database.model.dbo.FetchedDateDBO
import com.pichurchyk.nutrition.database.model.dbo.IntakeDBO
import com.pichurchyk.nutrition.database.model.dbo.WaterIntakeDBO
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
internal interface NutritionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveIntake(intake: IntakeDBO): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveWaterIntake(intake: WaterIntakeDBO): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveLastFetchedDate(fetchedDateDBO: FetchedDateDBO)

    @Query("SELECT * FROM FetchedDates WHERE date =:date")
    fun getFetchedDateInfo(date: Date): Flow<FetchedDateDBO?>

    @Query("SELECT * FROM Intake WHERE id = :id")
    suspend fun getIntakeById(id: Long): IntakeDBO?

    @Query("SELECT * FROM WaterIntake WHERE id = :id")
    suspend fun getWaterIntakeById(id: Long): WaterIntakeDBO?

    @Delete
    fun removeIntake(intake: IntakeDBO)

    @Query("SELECT * FROM Intake WHERE date BETWEEN :startOfDay AND :endOfDay")
    fun getDailyIntakes(startOfDay: Date, endOfDay: Date): Flow<List<IntakeDBO>>

    @Query("SELECT * FROM WaterIntake WHERE date BETWEEN :startOfDay AND :endOfDay")
    fun getDailyWaterIntakes(startOfDay: Date, endOfDay: Date): Flow<List<WaterIntakeDBO>>
}