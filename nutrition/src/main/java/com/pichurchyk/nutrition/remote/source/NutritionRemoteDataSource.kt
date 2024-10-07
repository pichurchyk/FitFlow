package com.pichurchyk.nutrition.remote.source

import com.pichurchyk.nutrition.database.model.dto.DailyInfoDTO
import com.pichurchyk.nutrition.remote.source.resource.IntakesResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

internal class NutritionRemoteDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getAllIntakes(): Flow<DailyInfoDTO> = flow {
        httpClient
            .get(IntakesResource()) {
                parameter("select", "*")
            }
            .body<Unit>()
            .also { emit(
                DailyInfoDTO(
                    date = Date(),
                    intakes = emptyList(),
                    caloriesSum = 10
                )
            ) }
    }
}