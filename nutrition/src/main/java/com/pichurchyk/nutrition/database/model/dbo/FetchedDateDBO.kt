package com.pichurchyk.nutrition.database.model.dbo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "FetchedDates")
data class FetchedDateDBO(
    @PrimaryKey val date: Date,
    val fetchedDate: Date
)