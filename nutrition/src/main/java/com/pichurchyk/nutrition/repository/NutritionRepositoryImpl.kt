package com.pichurchyk.nutrition.repository

import com.pichurchyk.fitflow.common.ext.date.toEndOfDay
import com.pichurchyk.fitflow.common.ext.date.toStartOfDay
import com.pichurchyk.nutrition.database.NutritionDao
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.database.model.dbo.FetchedDateDBO
import com.pichurchyk.nutrition.model.DailyInfo
import com.pichurchyk.nutrition.model.Intake
import com.pichurchyk.nutrition.model.IntakeValue
import com.pichurchyk.nutrition.model.WaterIntake
import com.pichurchyk.nutrition.model.create.CreateIntakeModel
import com.pichurchyk.nutrition.model.create.CreateWaterIntakeModel
import com.pichurchyk.nutrition.remote.source.NutritionRemoteDataSource
import com.pichurchyk.nutrition.toDBO
import com.pichurchyk.nutrition.toDomain
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
        intake.toDBO().let {
            localDataSource.saveIntake(it)
                .also { savedIntakeId ->
                    emit(Unit)

                    localDataSource.getIntakeById(savedIntakeId).let { savedIntakeDBO ->
                        savedIntakeDBO?.let {
                            val savedIntakeDTO = savedIntakeDBO.toDomain()

                            remoteDataSource.saveIntake(savedIntakeDTO).first()
                        }
                    }
                }
        }
    }

    override suspend fun saveWaterIntake(intake: CreateWaterIntakeModel): Flow<Unit> = flow {
        intake.toDBO().let {
            localDataSource.saveWaterIntake(it)
                .also { savedIntakeId ->
                    emit(Unit)

                    localDataSource.getWaterIntakeById(savedIntakeId).let { savedIntakeDBO ->
                        savedIntakeDBO?.let {
                            val savedIntakeDTO = savedIntakeDBO.toDomain()

                            remoteDataSource.saveWaterIntake(savedIntakeDTO).first()
                        }
                    }
                }
        }
    }

    override suspend fun removeIntake(intake: Intake): Flow<Unit> {
        return flow { }
    }

    override suspend fun getDailyIntakes(date: Date): Flow<DailyInfo> {
        return localDataSource.getDailyIntakes(
            startOfDay = date.toStartOfDay(),
            endOfDay = date.toEndOfDay()
        ).map { summary ->
            if (summary.isEmpty()) {
//                In case if our data is empty
                return@map DailyInfo(
                    date = date,
                    intakes = listOf(
                        Intake(
                            id = 0,
                            date = date,
                            values = listOf(
                                IntakeValue(0, IntakeType.FAT),
                                IntakeValue(0, IntakeType.PROTEIN),
                                IntakeValue(0, IntakeType.CARBS)
                            ),
                            calories = 0
                        )
                    )
                )
            }

            val intakes = summary.map { intakeDBO ->
                val existingValues = intakeDBO.values.map { it.toDomain() }

                val requiredTypes = listOf(IntakeType.FAT, IntakeType.PROTEIN, IntakeType.CARBS)

                val completeValues = requiredTypes.map { type ->
                    existingValues.find { it.intakeType == type } ?: IntakeValue(0, type)
                }

                intakeDBO.toDomain().copy(values = completeValues)
            }

            DailyInfo(
                date = date,
                intakes = intakes
            )
        }

    }

    override suspend fun getDailyWaterIntakes(date: Date): Flow<List<WaterIntake>> {
        return localDataSource.getDailyWaterIntakes(
            startOfDay = date.toStartOfDay(),
            endOfDay = date.toEndOfDay()
        )
            .map { summary ->
                summary.map { intake ->
                    intake.toDomain()
                }
            }
    }

    override suspend fun fetchRemoteAndLocal(date: Date) {
        val lastDateFetch = localDataSource.getFetchedDateInfo(date).first()?.fetchedDate

//       Check if time before previous fetch and now is more than 2 minutes
        val needFetchAgain =
            lastDateFetch?.let { lastFetch -> (Date().time - lastFetch.time) >= FETCH_UPDATE_LIMIT }

        if (needFetchAgain == true) {
            try {
                coroutineScope {
                    val intakes = IntakeType.getMainTypes().map { intakeType ->
                        async {
                            remoteDataSource.getAllIntakes(intakeType, date).first()
                        }
                    }.awaitAll()

                    val waterIntakes = async {
                        remoteDataSource.getWaterIntakes(date).first()
                    }.await()

                    intakes
                        .flatten()
                        .forEach { intakeDTO ->
                            if (localDataSource.getIntakeById(intakeDTO.id) == null) {
                                localDataSource.saveIntake(intakeDTO.toDBO())
                            }
                        }

                    localDataSource.saveLastFetchedDate(FetchedDateDBO(date.toStartOfDay(), Date()))

                    waterIntakes
                        .forEach { waterIntakeDTO ->
                            if (localDataSource.getWaterIntakeById(waterIntakeDTO.id) == null) {
                                localDataSource.saveWaterIntake(waterIntakeDTO.toDBO())
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
