package com.pichurchyk.nutrition.repository

import com.pichurchyk.fitflow.common.ext.date.toEndOfDay
import com.pichurchyk.fitflow.common.ext.date.toStartOfDay
import com.pichurchyk.nutrition.database.NutritionDao
import com.pichurchyk.nutrition.database.mapper.IntakeDatabaseMapper
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.database.model.dbo.FetchedDateDBO
import com.pichurchyk.nutrition.model.CreateIntakeModel
import com.pichurchyk.nutrition.model.DailyInfoDTO
import com.pichurchyk.nutrition.model.IntakeDTO
import com.pichurchyk.nutrition.remote.source.NutritionRemoteDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Date

internal class NutritionRepositoryImpl(
    private val remoteDataSource: NutritionRemoteDataSource,
    private val localDataSource: NutritionDao,
) : NutritionRepository {
    override suspend fun saveIntake(intake: CreateIntakeModel): Flow<Unit> = flow {
        IntakeDatabaseMapper.fromCreationModel(intake).let {
            localDataSource.saveIntake(it)
                .also { savedIntakeId ->
                    emit(Unit)

                    localDataSource.getIntakeById(savedIntakeId).let { savedIntakeDBO ->
                        savedIntakeDBO?.let {
                            val savedIntakeDTO = IntakeDatabaseMapper.fromDbo(savedIntakeDBO)

                            remoteDataSource.saveIntake(savedIntakeDTO).first()
                        }
                    }
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
    ): Flow<DailyInfoDTO> {
        return localDataSource.getDailyInfo(startOfDay = date.toStartOfDay(), endOfDay = date.toEndOfDay())
            .map { summary ->
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
                )
            }
    }

    override suspend fun fetchRemoteAndLocal(date: Date) {
        val lastDateFetch = localDataSource.getFetchedDateInfo(date).first()?.fetchedDate

//       Check if time before previous fetch and now is more than 2 minutes
        val needFetchAgain = lastDateFetch?.let { lastFetch -> (Date().time - lastFetch.time) >= FETCH_UPDATE_LIMIT }

        if (needFetchAgain == true) {
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
                            if (localDataSource.getIntakeById(intakeDTO.id) == null) {
                                localDataSource.saveIntake(IntakeDatabaseMapper.fromDto(intakeDTO))
                            }
                        }

                    localDataSource.saveLastFetchedDate(FetchedDateDBO(date.toStartOfDay(), Date()))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val FETCH_UPDATE_LIMIT = 2 * 60 * 1000L
    }
}
