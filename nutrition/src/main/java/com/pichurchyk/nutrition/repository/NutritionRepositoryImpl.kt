package com.pichurchyk.nutrition.repository

import com.pichurchyk.fitflow.common.ext.date.toEndOfDay
import com.pichurchyk.fitflow.common.ext.date.toStartOfDay
import com.pichurchyk.nutrition.database.NutritionDao
import com.pichurchyk.nutrition.database.mapper.IntakeDatabaseMapper
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.DailyInfoDTO
import com.pichurchyk.nutrition.model.IntakeDTO
import com.pichurchyk.nutrition.remote.source.NutritionRemoteDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.util.Date

internal class NutritionRepositoryImpl(
    private val remoteDataSource: NutritionRemoteDataSource,
    private val localDataSource: NutritionDao,
) : NutritionRepository {
    override suspend fun saveIntake(intake: IntakeDTO): Flow<Unit> = flow {
        IntakeDatabaseMapper.fromDto(intake).let {
            localDataSource.saveIntake(it)
                .also {
                    emit(Unit)
                }
        }
    }

    override suspend fun removeIntake(intake: IntakeDTO): Flow<Unit> {
        return flow { }
    }

    override suspend fun getAllIntakesByDateAndType(
        date: Date,
        type: IntakeType
    ): Flow<List<IntakeDTO>> = flow {
        localDataSource.getAllIntakesByDateAndType(date, type)
    }

    override suspend fun getDailyInfo(
        date: Date,
        needFetchWithRemote: Boolean
    ): Flow<DailyInfoDTO> = flow {
        localDataSource.getDailyInfo(startOfDay = date.toStartOfDay(), endOfDay = date.toEndOfDay())
            .collect { summary ->
                val intakes = summary.map { intake ->
                    IntakeDTO(
                        date = intake.date,
                        value = intake.totalValue,
                        type = intake.type,
                        id = intake.id
                    )
                }

                val caloriesSum =
                    intakes.filter { it.type == IntakeType.CALORIES }.sumOf { it.value }

                DailyInfoDTO(
                    date = date,
                    intakes = intakes,
                    caloriesSum = caloriesSum
                ).also {
                    emit(it)

                    fetchRemoteAndLocal(date)
                }
            }
    }

    // TODO: add token to requests
    // TODO: think about IDs
    private suspend fun fetchRemoteAndLocal(date: Date) {
        try {
            coroutineScope {
                val remoteData = IntakeType.getMainTypes().map { intakeType ->
                    async {
                        remoteDataSource.getAllIntakes(intakeType, date).first()
                    }
                }.awaitAll()

                remoteData
                    .flatten()
                    .forEach { intakeDTO ->
                        localDataSource.saveIntake(IntakeDatabaseMapper.fromDto(intakeDTO))
                    }
                    .also {
                        getDailyInfo(date, false)
                    }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}