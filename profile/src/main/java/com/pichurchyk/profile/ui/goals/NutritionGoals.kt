package com.pichurchyk.profile.ui.goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.pichurchyk.fitflow.common.ext.clearFocusOnClick
import com.pichurchyk.fitflow.common.ui.CommonButton
import com.pichurchyk.fitflow.common.ui.Loader
import com.pichurchyk.fitflow.common.ui.button.DefaultCommonButtonColors
import com.pichurchyk.fitflow.common.ui.theme.TextStyles
import com.pichurchyk.nutrition.model.goals.NutritionGoal
import com.pichurchyk.nutrition.ui.ext.getColor
import com.pichurchyk.nutrition.ui.ext.getUnit
import com.pichurchyk.profile.R
import com.pichurchyk.profile.ui.viewmodel.NutritionGoalsViewState
import com.pichurchyk.fitflow.common.R as commonR

@Composable
fun NutritionGoals(
    modifier: Modifier,
    state: NutritionGoalsViewState,
    onGoalChanged: (NutritionGoal) -> Unit,
    onSaveClick: () -> Unit,
    onDiscardClick: () -> Unit
) {
    val goalsValues = if (state is NutritionGoalsViewState.Changing) {
        state.newValues
    } else {
        state.values
    }

    Column(
        modifier
    ) {
        Text(
            modifier = Modifier.padding(top = 16.dp, start = 8.dp),
            style = TextStyles.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
            text = stringResource(R.string.macronutrient_goals)
        )

        Column(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NutritionGoalsPercentage(
                modifier = Modifier,
                intakes = goalsValues
            )

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                goalsValues.forEach { goal ->
                    NutritionGoalsItem(
                        modifier = Modifier.weight(1f),
                        title = stringResource(commonR.string.carbs),
                        value = goal.value.toString(),
                        subtitle = stringResource(goal.intakeType.getUnit()),
                        bgColor = goal.intakeType.getColor(),
                        onValueChanged = { newValue ->
                            val validatedValue = if (newValue.isEmpty()) 0 else newValue.toInt()
                            onGoalChanged(NutritionGoal(goal.id, goal.intakeType, validatedValue))
                        }
                    )
                }
            }

            if (state is NutritionGoalsViewState.Changing) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        CommonButton(
                            modifier = Modifier.fillMaxWidth().clearFocusOnClick(),
                            text = stringResource(com.pichurchyk.fitflow.common.R.string.save)
                        ) {
                            onSaveClick()
                        }
                    }

                    CommonButton(
                        modifier = Modifier.weight(1f).clearFocusOnClick(),
                        text = stringResource(com.pichurchyk.fitflow.common.R.string.discard),
                        colors = DefaultCommonButtonColors(
                            textColor = MaterialTheme.colorScheme.primary,
                            bgColor = MaterialTheme.colorScheme.onPrimary,
                            borderColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        onDiscardClick()
                    }
                }
            }

            if (state is NutritionGoalsViewState.Loading) {
                Box(
                    modifier = Modifier.size(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Loader(
                        modifier = Modifier,
                        size = 30.dp
                    )
                }
            }
        }
    }
}