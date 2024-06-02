package com.pichurchyk.nutrition.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pichurchyk.nutrition.database.model.dbo.IntakeDBO
import com.pichurchyk.nutrition.database.model.IntakeType
import java.util.Date

@Dao
interface NutritionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveIntake(intake: IntakeDBO)

    @Delete
    fun removeIntake(intake: IntakeDBO)

    @Query("SELECT * FROM Intake where date = :date AND type = :intakeType")
    fun getAllIntakesByDateAndType(date: Date, intakeType: IntakeType): List<IntakeDBO>
}