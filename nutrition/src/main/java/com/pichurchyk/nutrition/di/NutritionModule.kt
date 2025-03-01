package com.pichurchyk.nutrition.di

import com.pichurchyk.nutrition.database.NutritionDao
import com.pichurchyk.nutrition.database.NutritionDatabase
import com.pichurchyk.nutrition.remote.source.NutritionRemoteDataSource
import com.pichurchyk.nutrition.repository.NutritionRepository
import com.pichurchyk.nutrition.repository.NutritionRepositoryImpl
import com.pichurchyk.nutrition.usecase.FetchRemoteAndLocalUseCase
import com.pichurchyk.nutrition.usecase.GetDailyIntakesUseCase
import com.pichurchyk.nutrition.usecase.GetDailyWaterIntakesUseCase
import com.pichurchyk.nutrition.usecase.SaveIntakeUseCase
import com.pichurchyk.nutrition.usecase.SaveWaterIntakeUseCase
import org.koin.dsl.module

val nutritionModule = module {

    single { NutritionRemoteDataSource(get(), get()) }
    single { provideNutritionDatabaseDao(get()) }
    single { NutritionDatabase(get()) }
    single<NutritionRepository> {
        NutritionRepositoryImpl(
            localDataSource = get(),
            remoteDataSource = get()
        )
    }

//    Use Cases
    single { SaveIntakeUseCase(get()) }
    single { SaveWaterIntakeUseCase(get()) }
    single { GetDailyIntakesUseCase(get()) }
    single { GetDailyWaterIntakesUseCase(get()) }
    single { FetchRemoteAndLocalUseCase(get()) }
}

private fun provideNutritionDatabaseDao(database: NutritionDatabase): NutritionDao {
    return database.dao()
}
