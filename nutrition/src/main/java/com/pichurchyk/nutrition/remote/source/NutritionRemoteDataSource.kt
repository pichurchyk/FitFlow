package com.pichurchyk.nutrition.remote.source

import com.pichurchyk.fitflow.common.ext.date.toEndOfDay
import com.pichurchyk.fitflow.common.ext.date.toStartOfDay
import com.pichurchyk.fitflow.common.preferences.AuthPreferencesActions
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.IntakeDTO
import com.pichurchyk.nutrition.remote.mapper.IntakeRemoteMapper
import com.pichurchyk.nutrition.remote.model.IntakeResponse
import com.pichurchyk.nutrition.remote.source.resource.IntakesResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
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

    suspend fun getAllIntakes(type: IntakeType, date: Date): Flow<List<IntakeDTO>> = flow {
        httpClient
            .get(IntakesResource()) {
                parameter("type", "eq.$type")
                parameter("date", "gte.${date.toStartOfDay().time}")
                parameter("date", "lte.${date.toEndOfDay().time}")
            }
            .body<List<IntakeResponse>>()
            .let { intakes ->
                intakes.map { intake ->
                    IntakeRemoteMapper.fromRemote(intake)
                }
            }
            .also { emit(it) }
    }

    fun saveIntake(intakeDTO: IntakeDTO): Flow<Unit> = flow {
        httpClient
            .post(IntakesResource()) {
                setBody(IntakeRemoteMapper.fromDTO(intakeDTO, preferences.getUserUid().first()))
            }
            .body<Unit>()
            .also { emit(it) }
    }

    fun removeIntake(intakeDTO: IntakeDTO): Flow<Unit> = flow {
        httpClient
            .delete(IntakesResource()) {
                parameter("id", "eq.${intakeDTO.id}")
            }
            .body<Unit>()
            .also { emit(it) }
    }
}