package com.pichurchyk.nutrition.database.di

import com.pichurchyk.nutrition.repository.NutritionRepository
import com.pichurchyk.nutrition.database.NutritionDatabase
import com.pichurchyk.nutrition.database.repository.NutritionDatabaseRepositoryImpl
import com.pichurchyk.nutrition.usecase.GetDailyInfoUseCase
import com.pichurchyk.nutrition.usecase.SaveIntakeUseCase
import org.koin.dsl.module

val nutritionDatabaseModule = module {

    single { NutritionDatabase(get()) }
    single { provideNutritionDatabaseRepository(get()) }

//    Use Cases
    single { SaveIntakeUseCase(get()) }
    single { GetDailyInfoUseCase(get()) }

}
private fun provideNutritionDatabaseRepository(database: NutritionDatabase): NutritionRepository {
    return NutritionDatabaseRepositoryImpl(database.dao())
}
