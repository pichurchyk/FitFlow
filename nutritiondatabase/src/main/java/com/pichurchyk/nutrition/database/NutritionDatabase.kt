package com.pichurchyk.nutrition.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pichurchyk.nutrition.database.model.dbo.IntakeDBO

@Database(
    entities = [
        IntakeDBO::class
               ],
    version = 1
)
@TypeConverters(TypeConverter::class)
abstract class NutritionDatabase : RoomDatabase() {
    abstract fun dao(): NutritionDao
}

fun NutritionDatabase(applicationContext: Context): NutritionDatabase {
    return Room.databaseBuilder(
        applicationContext,
        NutritionDatabase::class.java,
        "NutritionDatabase"
    ).build()
}