package com.pichurchyk.nutrition.database.repository

import com.pichurchyk.nutrition.database.NutritionDao
import com.pichurchyk.nutrition.database.mapper.IntakeMapper
import com.pichurchyk.nutrition.database.model.IntakeType
import com.pichurchyk.nutrition.database.model.dto.DailyInfoDTO
import com.pichurchyk.nutrition.database.model.dto.IntakeDTO
import com.pichurchyk.nutrition.database.model.dto.getIntakesSum
import java.util.Date

internal class NutritionDatabaseRepositoryImpl(private val dao: NutritionDao): NutritionDatabaseRepository {
    override suspend fun saveIntake(intake: IntakeDTO) {
        IntakeMapper.fromDto(intake).let {
            dao.saveIntake(it)
        }
    }

    override suspend fun removeIntake(intake: IntakeDTO) {
        IntakeMapper.fromDto(intake).let {
            dao.saveIntake(it)
        }
    }

    override suspend fun getAllIntakesByDateAndType(date: Date, type: IntakeType): List<IntakeDTO> {
        val dboResponse = dao.getAllIntakesByDateAndType(date, type)

        return dboResponse.map { IntakeMapper.fromDbo(it) }
    }

    override suspend fun getDailyInfo(date: Date): DailyInfoDTO {
        val intakes = IntakeType.entries.map { intakeType ->
            IntakeDTO(
                date = date,
                value = getAllIntakesByDateAndType(date, intakeType).getIntakesSum(),
                type = intakeType
            )
        }

        return DailyInfoDTO(
            date = date,
            intakes = intakes
        )
    }
}