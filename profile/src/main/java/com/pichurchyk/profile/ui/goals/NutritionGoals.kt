package com.pichurchyk.profile.ui.goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ui.theme.AppTheme
import com.pichurchyk.fitflow.common.ui.theme.color_carbs
import com.pichurchyk.fitflow.common.ui.theme.color_fat
import com.pichurchyk.fitflow.common.ui.theme.color_protein
import com.pichurchyk.fitflow.common.R as commonR

@Composable
fun NutritionGoals(
    modifier: Modifier
) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        NutritionGoalsItem(
            modifier = Modifier.weight(1f),
            title = stringResource(commonR.string.carbs),
            value = "200",
            subtitle = stringResource(commonR.string.unit_gram_long),
            bgColor = color_carbs,
            onValueChanged = {}
        )

        NutritionGoalsItem(
            modifier = Modifier.weight(1f),
            title = stringResource(commonR.string.protein),
            value = "80",
            subtitle = stringResource(commonR.string.unit_gram_long),
            bgColor = color_protein,
            onValueChanged = {}
        )

        NutritionGoalsItem(
            modifier = Modifier.weight(1f),
            title = stringResource(commonR.string.fat),
            value = "50",
            subtitle = stringResource(commonR.string.unit_gram_long),
            bgColor = color_fat,
            onValueChanged = {}
        )
    }
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        NutritionGoals(Modifier)
    }
}