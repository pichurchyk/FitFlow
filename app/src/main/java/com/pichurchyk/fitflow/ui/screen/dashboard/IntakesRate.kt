package com.pichurchyk.fitflow.ui.screen.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.ui.screen.dashboard.components.LinearIntakeRateItem
import com.pichurchyk.fitflow.ui.theme.AppTheme
import com.pichurchyk.nutrition.database.model.IntakeType

@Composable
fun IntakesRate(
    modifier: Modifier = Modifier,
    fat: Int,
    protein: Int,
    carbs: Int
) {
    val carbsRate = IntakeRate(type = IntakeType.CARBS, value = carbs)
    val fatRate = IntakeRate(type = IntakeType.FAT, value = fat)
    val proteinRate = IntakeRate(type = IntakeType.PROTEIN, value = protein)
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        LinearIntakeRateItem(modifier = Modifier.fillMaxWidth(), intake = carbsRate, limit = 120)
        LinearIntakeRateItem(modifier = Modifier.fillMaxWidth(), intake = proteinRate, limit = 186)
        LinearIntakeRateItem(modifier = Modifier.fillMaxWidth(), intake = fatRate, limit = 65)
    }
}

@Preview
@Composable
private fun Preview() {
    AppTheme {
        IntakesRate(fat = 10, protein = 70, carbs = 120)
    }
}

data class IntakeRate(
    val value: Int,
    val type: IntakeType
)
