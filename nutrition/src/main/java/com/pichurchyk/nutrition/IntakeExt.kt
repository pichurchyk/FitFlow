package com.pichurchyk.nutrition

import com.pichurchyk.nutrition.database.model.dbo.IntakeDBO
import com.pichurchyk.nutrition.database.model.dbo.IntakeValueDBO
import com.pichurchyk.nutrition.database.model.dbo.WaterIntakeDBO
import com.pichurchyk.nutrition.model.Intake
import com.pichurchyk.nutrition.model.IntakeValue
import com.pichurchyk.nutrition.model.WaterIntake
import com.pichurchyk.nutrition.model.create.CreateIntakeModel
import com.pichurchyk.nutrition.model.create.CreateIntakeValueModel
import com.pichurchyk.nutrition.model.create.CreateWaterIntakeModel
import com.pichurchyk.nutrition.remote.model.IntakeResponse
import com.pichurchyk.nutrition.remote.model.IntakeValueResponse
import com.pichurchyk.nutrition.remote.model.WaterIntakeResponse
import com.pichurchyk.nutrition.remote.model.payload.IntakePayload
import com.pichurchyk.nutrition.remote.model.payload.IntakeValuePayload
import com.pichurchyk.nutrition.remote.model.payload.WaterIntakePayload
import java.util.Date

fun Intake.toDBO(): IntakeDBO = IntakeDBO(
    date = this.date,
    values = this.values.map { it.toDBO() },
    calories = this.calories,
    id = this.id
)

fun CreateIntakeModel.toDBO(): IntakeDBO = IntakeDBO(
    date = this.date,
    values = this.values.map { it.toDBO() },
    calories = this.calories
)

fun CreateIntakeValueModel.toDBO(): IntakeValueDBO = IntakeValueDBO(
    value = this.value,
    intakeType = this.intakeType
)

fun IntakeDBO.toDomain(): Intake = Intake(
    date = this.date,
    values = this.values.map { it.toDomain() },
    calories = this.calories,
    id = this.id
)

fun IntakeValue.toDBO(): IntakeValueDBO = IntakeValueDBO(
    value = this.value,
    intakeType = this.intakeType
)

fun IntakeValueDBO.toDomain(): IntakeValue = IntakeValue(
    value = this.value,
    intakeType = this.intakeType
)

fun IntakeResponse.toDomain(): Intake = Intake(
    id = this.id,
    date = Date(this.date),
    values = this.values.map { it.toDomain() },
    calories = this.calories
)

fun Intake.toPayload(userUuid: String): IntakePayload = IntakePayload(
    date = this.date.time,
    values = this.values.map { it.toPayload() },
    calories = this.calories,
    userUuid = userUuid
)

fun IntakeValueResponse.toDomain(): IntakeValue = IntakeValue(
    value = this.value,
    intakeType = this.type
)

fun IntakeValue.toPayload(): IntakeValuePayload = IntakeValuePayload(
    value = this.value,
    intakeType = this.intakeType
)

fun WaterIntake.toDBO() = WaterIntakeDBO(
    value = this.value,
    date = this.date
)

fun WaterIntakeDBO.toDomain() = WaterIntake(
    value = this.value,
    date = this.date
)

fun WaterIntake.toPayload(userUuid: String): WaterIntakePayload = WaterIntakePayload(
    value = this.value,
    date = this.date.time,
    userUuid = userUuid
)

fun CreateWaterIntakeModel.toDBO() = WaterIntakeDBO(
    value = this.value,
    date = this.date
)

fun WaterIntakeResponse.toDomain() = WaterIntake(
    value = this.value,
    date = Date(this.date)
)