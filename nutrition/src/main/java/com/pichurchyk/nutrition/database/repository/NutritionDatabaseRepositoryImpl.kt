package com.pichurchyk.nutrition.database.repository

import com.pichurchyk.fitflow.common.ext.date.toEndOfDay
import com.pichurchyk.fitflow.common.ext.date.toStartOfDay
import com.pichurchyk.nutrition.database.NutritionDao
import com.pichurchyk.nutrition.database.mapper.IntakeDatabaseMapper
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.model.DailyInfoDTO
import com.pichurchyk.nutrition.model.IntakeDTO
import com.pichurchyk.nutrition.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Date

internal class NutritionDatabaseRepositoryImpl(private val dao: NutritionDao) :
    NutritionRepository {
    override suspend fun saveIntake(intake: IntakeDTO) = flow {
        IntakeDatabaseMapper.fromDto(intake).let {
            dao.saveIntake(it)
                .also {
                    emit(Unit)
                }
        }
    }

    override suspend fun removeIntake(intake: IntakeDTO): Flow<Unit> = flow {
        IntakeDatabaseMapper.fromDto(intake).let {
            dao.removeIntake(it)
        }
    }

    override suspend fun getAllIntakesByDateAndType(date: Date, type: IntakeType): Flow<List<IntakeDTO>> {
        val dboResponse = dao.getAllIntakesByDateAndType(date, type)

        return dboResponse.map { responseItems -> responseItems.map { IntakeDatabaseMapper.fromDbo(it) } }
    }

    override suspend fun getDailyInfo(date: Date): Flow<DailyInfoDTO> {
        return dao.getDailyInfo(startOfDay = date.toStartOfDay(), endOfDay = date.toEndOfDay())
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
}