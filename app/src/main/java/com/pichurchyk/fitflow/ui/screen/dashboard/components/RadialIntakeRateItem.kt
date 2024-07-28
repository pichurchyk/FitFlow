package com.pichurchyk.fitflow.ui.screen.dashboard.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pichurchyk.fitflow.ui.common.RadialProgress
import com.pichurchyk.fitflow.ui.screen.dashboard.IntakeRate
import com.pichurchyk.fitflow.ui.theme.AppTheme
import com.pichurchyk.nutrition.database.model.IntakeType

@Composable
fun RadialIntakeRateItem(modifier: Modifier = Modifier, intake: IntakeRate, limit: Int) {
    RadialProgress(modifier = modifier, value = intake.value, limit = limit)
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        RadialIntakeRateItem(
            modifier = Modifier,
            intake = IntakeRate(100, IntakeType.WATER),
            limit = 100
        )
    }
}