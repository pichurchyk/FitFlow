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
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.goals.NutritionGoal
import com.pichurchyk.nutrition.ui.ext.getColor
import com.pichurchyk.nutrition.ui.ext.getUnit
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

            goals.forEach { goal ->
                item(span = { GridItemSpan(1) }) {
                    NutritionGoalsItem(
                        modifier = Modifier,
                        title = stringResource(commonR.string.carbs),
                        value = goal.value.toString(),
                        subtitle = stringResource(goal.intakeType.getUnit()),
                        bgColor = goal.intakeType.getColor(),
                        onValueChanged = {
                            onGoalChanged(NutritionGoal(goal.intakeType, it.toInt()))
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun Preview() {
    AppTheme {
        NutritionGoals(
            modifier = Modifier,
            goals = listOf(
                NutritionGoal(IntakeType.FAT, 60),
                NutritionGoal(IntakeType.PROTEIN, 140),
                NutritionGoal(IntakeType.FAT, 200),
            ),
            onGoalChanged = {}
        )
    }
}