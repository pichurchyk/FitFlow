package com.pichurchyk.nutrition.database.repository

import com.pichurchyk.fitflow.common.ext.date.toEndOfDay
import com.pichurchyk.fitflow.common.ext.date.toStartOfDay
import com.pichurchyk.nutrition.database.NutritionDao
import com.pichurchyk.nutrition.database.mapper.IntakeMapper
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.database.model.dto.DailyInfoDTO
import com.pichurchyk.nutrition.database.model.dto.IntakeDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Date

internal class NutritionDatabaseRepositoryImpl(private val dao: NutritionDao) :
    NutritionDatabaseRepository {
    override suspend fun saveIntake(intake: IntakeDTO) = flow {
        IntakeMapper.fromDto(intake).let {
            dao.saveIntake(it)
                .also {
                    emit(Unit)
                }
        }
    }

    override suspend fun removeIntake(intake: IntakeDTO) {
        IntakeMapper.fromDto(intake).let {
            dao.removeIntake(it)
        }
    }

    override suspend fun getAllIntakesByDateAndType(date: Date, type: IntakeType): List<IntakeDTO> {
        val dboResponse = dao.getAllIntakesByDateAndType(date, type)

        return dboResponse.map { IntakeMapper.fromDbo(it) }
    }

    override suspend fun getDailyInfo(date: Date): Flow<DailyInfoDTO> = flow {
        dao.getDailyInfo(startOfDay = date.toStartOfDay(), endOfDay = date.toEndOfDay())
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
                }
            }
    }
}