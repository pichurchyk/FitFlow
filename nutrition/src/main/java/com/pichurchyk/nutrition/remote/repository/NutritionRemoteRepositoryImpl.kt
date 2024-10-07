package com.pichurchyk.nutrition.remote.repository

import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.database.model.dto.DailyInfoDTO
import com.pichurchyk.nutrition.database.model.dto.IntakeDTO
import com.pichurchyk.nutrition.remote.source.NutritionRemoteDataSource
import com.pichurchyk.nutrition.repository.NutritionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.Dispatcher
import java.util.Date

internal class NutritionRemoteRepositoryImpl(
    private val dataSource: NutritionRemoteDataSource
) :
    NutritionRepository {
    override suspend fun saveIntake(intake: IntakeDTO): Flow<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun removeIntake(intake: IntakeDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllIntakesByDateAndType(date: Date, type: IntakeType): List<IntakeDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyInfo(date: Date): Flow<DailyInfoDTO> =
        dataSource.getAllIntakes().flowOn(Dispatchers.IO)
}