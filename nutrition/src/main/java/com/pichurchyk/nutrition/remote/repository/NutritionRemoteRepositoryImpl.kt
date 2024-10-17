package com.pichurchyk.nutrition.remote.repository

import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.DailyInfoDTO
import com.pichurchyk.nutrition.model.IntakeDTO
import com.pichurchyk.nutrition.remote.source.NutritionRemoteDataSource
import com.pichurchyk.nutrition.repository.NutritionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

internal class NutritionRemoteRepositoryImpl(
    private val dataSource: NutritionRemoteDataSource
) : NutritionRepository {

    override suspend fun saveIntake(intake: IntakeDTO): Flow<Unit> =
        dataSource.saveIntake(intake).flowOn(Dispatchers.IO)

    override suspend fun removeIntake(intake: IntakeDTO): Flow<Unit> =
        dataSource.removeIntake(intake).flowOn(Dispatchers.IO)

    override suspend fun getAllIntakesByDateAndType(date: Date, type: IntakeType): Flow<List<IntakeDTO>> =
        dataSource.getAllIntakes(type, date).flowOn(Dispatchers.IO)

    override suspend fun getDailyInfo(date: Date): Flow<DailyInfoDTO> = flow {
        val intakesCalls = IntakeType.getMainTypes()
            .map { intakeType ->
                dataSource.getAllIntakes(type = intakeType, date)
            }

        intakesCalls
            .map { intakeFlow ->
                intakeFlow.first()
            }
            .let { results ->
                val resultsList = results.flatten()
                val calories =
                    resultsList.filter { it.type == IntakeType.CALORIES }.sumOf { it.value }

                val dailyInfo = DailyInfoDTO(
                    date = date,
                    intakes = resultsList,
                    caloriesSum = calories
                )

                emit(dailyInfo)
            }
    }
}