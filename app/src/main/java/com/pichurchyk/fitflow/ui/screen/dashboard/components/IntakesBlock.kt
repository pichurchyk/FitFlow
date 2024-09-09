package com.pichurchyk.fitflow.ui.screen.dashboard.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.ext.getTitle
import com.pichurchyk.fitflow.ui.ext.getUnitWithValue
import com.pichurchyk.fitflow.ui.screen.dashboard.IntakeRate
import com.pichurchyk.fitflow.ui.theme.AppTheme
import com.pichurchyk.nutrition.database.model.IntakeType

@Composable
fun IntakesBlock(
    modifier: Modifier = Modifier,
    calories: Int,
    fat: Int,
    protein: Int,
    carbs: Int
) {
    val carbsRate = IntakeRate(type = IntakeType.CARBS, value = carbs)
    val fatRate = IntakeRate(type = IntakeType.FAT, value = fat)
    val proteinRate = IntakeRate(type = IntakeType.PROTEIN, value = protein)
    val caloriesRate = IntakeRate(type = IntakeType.CALORIES, value = calories)

    val items = listOf(caloriesRate, carbsRate, proteinRate, fatRate)

    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(items) { index, item ->
            val isFirstElement = index == 0
            val isLastElement = index == items.size - 1

            DashboardItemWrapper(
                type = DashboardItemWrapperType.FULL,
                title = if (isFirstElement) stringResource(id = R.string.intakes) else null,
                subtitle = stringResource(item.type.getTitle()),
                needBottomRadius = if (isLastElement) true else false,
                mainText = stringResource(id = item.type.getUnitWithValue(), item.value),
                content = {
                    AnimatedProgressIndicator(
                        modifier = Modifier,
                        value = calories,
                        type = AnimatedProgressIndicatorType.RADIAL,
                        color = MaterialTheme.colorScheme.primary,
                        limit = 2000
                    )
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun Preview() {
    AppTheme {
        IntakesBlock(
            calories = 123,
            fat = 10,
            protein = 70,
            carbs = 120
        )
    }
}