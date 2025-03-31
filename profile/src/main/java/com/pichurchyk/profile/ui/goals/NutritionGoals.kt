package com.pichurchyk.profile.ui.goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ui.theme.AppTheme
import com.pichurchyk.fitflow.common.ui.theme.TextStyles
import com.pichurchyk.fitflow.common.ui.theme.color_carbs
import com.pichurchyk.fitflow.common.ui.theme.color_fat
import com.pichurchyk.fitflow.common.ui.theme.color_protein
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.NutritionGoal
import com.pichurchyk.profile.R
import com.pichurchyk.fitflow.common.R as commonR

@Composable
fun NutritionGoals(
    modifier: Modifier,
    goals: List<NutritionGoal>,
    onGoalChanged: (NutritionGoal) -> Unit
) {
    Column(
        modifier
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 8.dp),
            style = TextStyles.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            text = stringResource(R.string.macronutrient_goals)
        )

        LazyVerticalGrid(
            modifier = Modifier.padding(top = 4.dp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item(span = { GridItemSpan(3) }) {
                NutritionGoalsPercentage(
                    modifier = Modifier,
                    intakes = goals
                )
            }

            item(span = { GridItemSpan(1) }) {
                NutritionGoalsItem(
                    modifier = Modifier,
                    title = stringResource(commonR.string.carbs),
                    value = "200",
                    subtitle = stringResource(commonR.string.unit_gram_long),
                    bgColor = color_carbs,
                    onValueChanged = {
                        onGoalChanged(NutritionGoal(IntakeType.CARBS, it.toInt()))
                    }
                )
            }
            item(span = { GridItemSpan(1) }) {
                NutritionGoalsItem(
                    modifier = Modifier,
                    title = stringResource(commonR.string.protein),
                    value = "80",
                    subtitle = stringResource(commonR.string.unit_gram_long),
                    bgColor = color_protein,
                    onValueChanged = {
                        onGoalChanged(NutritionGoal(IntakeType.PROTEIN, it.toInt()))
                    }
                )
            }
            item(span = { GridItemSpan(1) }) {
                NutritionGoalsItem(
                    modifier = Modifier,
                    title = stringResource(commonR.string.fat),
                    value = "50",
                    subtitle = stringResource(commonR.string.unit_gram_long),
                    bgColor = color_fat,
                    onValueChanged = {
                        onGoalChanged(NutritionGoal(IntakeType.FAT, it.toInt()))
                    }
                )
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        NutritionGoals(Modifier)
    }
}