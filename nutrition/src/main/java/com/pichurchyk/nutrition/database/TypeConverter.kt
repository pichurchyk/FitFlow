package com.pichurchyk.nutrition.database

import androidx.room.TypeConverter
import com.pichurchyk.nutrition.database.model.dbo.IntakeValueDBO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.Date

internal class TypeConverter {

    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(value: Long): Date = Date(value)

    @TypeConverter
    fun fromIntakeValueList(values: List<IntakeValueDBO>): String {
        return Json.encodeToString(values)
    }

    @TypeConverter
    fun toIntakeValueList(valuesString: String): List<IntakeValueDBO> {
        return Json.decodeFromString(valuesString)
    }
}