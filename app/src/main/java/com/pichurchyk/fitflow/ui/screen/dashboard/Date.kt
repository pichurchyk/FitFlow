package com.pichurchyk.fitflow.ui.screen.dashboard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.common.ext.getNextDay
import com.pichurchyk.fitflow.common.ext.isToday
import com.pichurchyk.fitflow.common.ext.toDateString
import com.pichurchyk.fitflow.ui.ext.doOnClick
import com.pichurchyk.fitflow.ui.theme.AppTheme
import java.util.Date

@Composable
fun Date(
    modifier: Modifier = Modifier,
    date: Date,
    onDateClick: () -> Unit,
    onNextClick: () -> Unit,
    onPreviousClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(6.dp)
                .doOnClick { onPreviousClick.invoke() },
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onSurface
        )
        Card(
            modifier = Modifier
                .doOnClick {
                    onDateClick.invoke()
                }
                .widthIn(min = 150.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            ),
            shape = RoundedCornerShape(6.dp),
        ) {
            val dateString =
                if (date.isToday()) stringResource(id = R.string.today) else date.toDateString()

            Text(
                modifier = Modifier
                    .widthIn(150.dp)
                    .padding(10.dp),
                text = dateString,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
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
        Date(modifier = Modifier, date = Date().getNextDay(), {}, {}, {})
    }
}