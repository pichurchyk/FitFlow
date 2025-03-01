package com.pichurchyk.nutrition.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pichurchyk.nutrition.database.model.dbo.FetchedDateDBO
import com.pichurchyk.nutrition.database.model.dbo.IntakeDBO
import com.pichurchyk.nutrition.database.model.dbo.WaterIntakeDBO

@Database(
    entities = [
        IntakeDBO::class,
        WaterIntakeDBO::class,
        FetchedDateDBO::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(TypeConverter::class)
internal abstract class NutritionDatabase : RoomDatabase() {
    abstract fun dao(): NutritionDao
}

internal fun NutritionDatabase(applicationContext: Context): NutritionDatabase {
    return Room.databaseBuilder(
        applicationContext,
        NutritionDatabase::class.java,
        "NutritionDatabase"
    ).build()
}