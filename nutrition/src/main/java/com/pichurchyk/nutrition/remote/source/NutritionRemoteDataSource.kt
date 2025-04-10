package com.pichurchyk.nutrition.remote.source

import com.pichurchyk.fitflow.common.ext.date.toEndOfDay
import com.pichurchyk.fitflow.common.ext.date.toStartOfDay
import com.pichurchyk.fitflow.common.preferences.AuthPreferencesActions
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.Intake
import com.pichurchyk.nutrition.model.WaterIntake
import com.pichurchyk.nutrition.remote.model.IntakeResponse
import com.pichurchyk.nutrition.remote.model.NutritionGoalResponse
import com.pichurchyk.nutrition.remote.model.WaterIntakeResponse
import com.pichurchyk.nutrition.remote.source.resource.GoalsResource
import com.pichurchyk.nutrition.remote.source.resource.IntakesResource
import com.pichurchyk.nutrition.remote.source.resource.WaterIntakesResource
import com.pichurchyk.nutrition.model.ext.toDomain
import com.pichurchyk.nutrition.model.ext.toPayload
import com.pichurchyk.nutrition.model.goals.NutritionGoal
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.patch
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.util.Date

internal class NutritionRemoteDataSource(
    private val httpClient: HttpClient,
    private val preferences: AuthPreferencesActions
) {

    suspend fun getAllIntakes(type: IntakeType, date: Date): Flow<List<Intake>> = flow {
        httpClient
            .get(IntakesResource()) {
                parameter("type", "eq.$type")
                parameter("date", "gte.${date.toStartOfDay().time}")
                parameter("date", "lte.${date.toEndOfDay().time}")
            }
            .body<List<IntakeResponse>>()
            .let { intakes ->
                intakes.map { intake ->
                    intake.toDomain()
                }
            }
            .also { emit(it) }
    }

    suspend fun getWaterIntakes(date: Date): Flow<List<WaterIntake>> = flow {
        httpClient
            .get(WaterIntakesResource()) {
                parameter("date", "gte.${date.toStartOfDay().time}")
                parameter("date", "lte.${date.toEndOfDay().time}")
            }
            .body<List<WaterIntakeResponse>>()
            .let { intakes ->
                intakes.map { intake ->
                    intake.toDomain()
                }
            }
            .also { emit(it) }
    }

    fun saveIntake(intake: Intake): Flow<Unit> = flow {
        httpClient
            // TODO: fix EDGE function in SUPABASE
            .post(IntakesResource()) {
                setBody(intake.toPayload(preferences.getUserUid().first()))
            }
            .body<Unit>()
            .also { emit(it) }
    }

    fun saveWaterIntake(intake: WaterIntake): Flow<Unit> = flow {
        httpClient
            .post(WaterIntakesResource()) {
                setBody(intake.toPayload(preferences.getUserUid().first()))
            }
            .body<Unit>()
            .also { emit(it) }
    }

    fun getUserGoals(): Flow<List<NutritionGoalResponse>> = flow {
        httpClient
            .get(GoalsResource())
            .body<List<NutritionGoalResponse>>()
            .also { emit(it) }
    }

    fun updateUserGoal(goal: NutritionGoal): Flow<NutritionGoal> = flow {
        httpClient
            .patch(GoalsResource()) {
                setBody(goal.toPayload())
            }
            .body<Unit>()
            .also {
                emit(goal)
            }
    }

    fun removeIntake(intake: Intake): Flow<Unit> = flow {
        httpClient
            .delete(IntakesResource()) {
                parameter("id", "eq.${intake.id}")
            }
            .body<Unit>()
            .also { emit(it) }
    }
}