package com.pichurchyk.nutrition.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pichurchyk.nutrition.database.model.dbo.IntakeDBO
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.database.model.dbo.DailyIntakeSummary
import com.pichurchyk.nutrition.database.model.dbo.FetchedDateDBO
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
internal interface NutritionDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveIntake(intake: IntakeDBO): Long

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveLastFetchedDate(fetchedDateDBO: FetchedDateDBO)

    @Query("SELECT * FROM FetchedDates WHERE date =:date")
    fun getFetchedDateInfo(date: Date): Flow<FetchedDateDBO?>

    @Query("SELECT * FROM Intake WHERE id = :id")
    suspend fun getIntakeById(id: Long): IntakeDBO?

    @Delete
    fun removeIntake(intake: IntakeDBO)

    @Query("SELECT * FROM Intake where date = :date AND type = :intakeType")
    fun getAllIntakesByDateAndType(date: Date, intakeType: IntakeType): Flow<List<IntakeDBO>>

    @Query("SELECT * FROM DailyIntakeSummary WHERE date BETWEEN :startOfDay AND :endOfDay")
    fun getDailyInfo(startOfDay: Date, endOfDay: Date): Flow<List<DailyIntakeSummary>>
}