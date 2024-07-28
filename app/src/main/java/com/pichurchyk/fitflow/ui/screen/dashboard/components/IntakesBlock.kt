package com.pichurchyk.fitflow.ui.screen.dashboard.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.pichurchyk.fitflow.R
import com.pichurchyk.fitflow.ui.common.RadialProgress
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

    Column(
        modifier = modifier
    ) {
        DashboardItemWrapper(
            type = DashboardItemWrapperType.FULL,
            title = R.string.intakes,
            subtitle = R.string.calories,
            needBottomRadius = false,
            mainText = stringResource(id = R.string.kcal_value, calories),
            content = {
                RadialProgress(value = calories, limit = 2000)
            }
        )

        DashboardItemWrapper(
            type = DashboardItemWrapperType.FULL,
            subtitle = R.string.carbs,
            mainText = stringResource(id = R.string.g_value, carbs),
            needBottomRadius = false,
            content = {
                RadialProgress(
                    modifier = Modifier,
                    value = carbsRate.value,
                    limit = 100
                )
            }
        )

        DashboardItemWrapper(
            type = DashboardItemWrapperType.FULL,
            subtitle = R.string.protein,
            mainText = stringResource(id = R.string.g_value, protein),
            needBottomRadius = false,
            content = {
                RadialProgress(
                    modifier = Modifier,
                    value = proteinRate.value,
                    limit = 100
                )
            }
        )

        DashboardItemWrapper(
            type = DashboardItemWrapperType.FULL,
            subtitle = R.string.fat,
            mainText = stringResource(id = R.string.g_value, fat),
            content = {
                RadialProgress(
                    modifier = Modifier,
                    value = fatRate.value,
                    limit = 100
                )
            }
        )
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