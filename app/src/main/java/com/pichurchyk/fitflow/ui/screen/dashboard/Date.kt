package com.pichurchyk.fitflow.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pichurchyk.fitflow.common.R
import com.pichurchyk.fitflow.common.ext.date.getNextDay
import com.pichurchyk.fitflow.common.ext.date.isCurrentYear
import com.pichurchyk.fitflow.common.ext.date.isToday
import com.pichurchyk.fitflow.common.ext.date.isTomorrow
import com.pichurchyk.fitflow.common.ext.date.isYesterday
import com.pichurchyk.fitflow.common.ext.date.toDateString
import com.pichurchyk.fitflow.common.utils.date.DateFormat
import com.pichurchyk.fitflow.ui.ext.doOnClick
import com.pichurchyk.fitflow.ui.theme.AppTheme
import java.util.Date
import java.util.Locale

@Composable
fun DateSelector(
    modifier: Modifier = Modifier,
    date: Date,
    onDateClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    val dateString = when {
        date.isToday() -> stringResource(id = R.string.today)
        date.isTomorrow() -> stringResource(id = R.string.tomorrow)
        date.isYesterday() -> stringResource(id = R.string.yesterday)
        date.isCurrentYear() -> date.toDateString(format = DateFormat.DAY_SHORT_MONTH)
        else -> date.toDateString()
    }.replaceFirstChar { if (it. isLowerCase()) it. titlecase(Locale.ROOT) else it. toString() }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .padding(6.dp)
                .doOnClick { onPreviousClick.invoke() },
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurface
        )

        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = dateString,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )

            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterEnd)
                    .doOnClick { onDateClick.invoke() },
                imageVector = Icons.Rounded.DateRange,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Icon(
            modifier = Modifier
                .padding(6.dp)
                .doOnClick { onNextClick.invoke() },
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}


@Composable
@Preview
private fun Preview() {
    AppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            DateSelector(modifier = Modifier, date = Date().getNextDay(), {}, {}, {})
        }
    }
}