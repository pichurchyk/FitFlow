package com.pichurchyk.fitflow.ui.screen.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.ext.getColor
import com.pichurchyk.fitflow.ui.screen.dashboard.IntakeRate
import com.pichurchyk.fitflow.ui.theme.AppTheme
import com.pichurchyk.fitflow.ui.theme.TextStyles
import com.pichurchyk.nutrition.database.model.IntakeType

@Composable
fun LinearPercentage(modifier: Modifier = Modifier, intake: IntakeRate, limit: Int) {
    Row(
        modifier = modifier
            .wrapContentSize(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        AnimatedProgressIndicator(
            modifier = Modifier
                .height(10.dp)
                .widthIn(max = 150.dp),
            type = AnimatedProgressIndicatorType.LINEAR,
            value = intake.value,
            color = intake.type.getColor(),
            limit = limit,
        )

        Text(
            modifier = Modifier
                .padding(start = 16.dp)
                .wrapContentSize(align = Alignment.CenterEnd, unbounded = true),
            text = stringResource(id = R.string.g_value, limit),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = TextStyles.bodySmall,
            textAlign = TextAlign.End
        )
    }
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        LinearPercentage(
            modifier = Modifier,
            intake = IntakeRate(100, IntakeType.WATER),
            limit = 100
        )
    }
}