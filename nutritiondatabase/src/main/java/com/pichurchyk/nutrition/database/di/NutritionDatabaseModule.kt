package com.pichurchyk.nutrition.database.di

import com.pichurchyk.nutrition.database.NutritionDatabase
import com.pichurchyk.nutrition.database.repository.NutritionDatabaseRepository
import com.pichurchyk.nutrition.database.repository.NutritionDatabaseRepositoryImpl
import com.pichurchyk.nutrition.database.usecase.GetDailyInfoUseCase
import com.pichurchyk.nutrition.database.usecase.SaveIntakeUseCase
import org.koin.dsl.module

val nutritionDatabaseModule = module {

    single { NutritionDatabase(get()) }
    single { provideNutrientRepository(get()) }

//    Use Cases
    single { SaveIntakeUseCase(get()) }
    single { GetDailyInfoUseCase(get()) }

}
private fun provideNutrientRepository(database: NutritionDatabase): NutritionDatabaseRepository {
    return NutritionDatabaseRepositoryImpl(database.dao())
}
