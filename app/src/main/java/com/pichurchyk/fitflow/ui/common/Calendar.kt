package com.pichurchyk.fitflow.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.common.ext.date.toEndOfDay
import java.time.Instant
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calendar(
    modifier: Modifier = Modifier,
    selectedDate: Date,
    onDateSelected: (Date) -> Unit,
    onDismiss: () -> Unit
) {
    val state = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Picker,
        selectableDates = DatePickerDefaults.AllDates,
        initialSelectedDateMillis = selectedDate.toEndOfDay().time
    )

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                    state.selectedDateMillis?.let {
                        val newSelectedDate = Date.from(Instant.ofEpochMilli(it))
                        onDateSelected(newSelectedDate)
                    }
                },
                text = stringResource(id = R.string.submit)
            )
        },
        content = {
            DatePicker(
                state = state,
                showModeToggle = false,
                dateFormatter = DatePickerDefaults.dateFormatter()
            )
        }
    )
}