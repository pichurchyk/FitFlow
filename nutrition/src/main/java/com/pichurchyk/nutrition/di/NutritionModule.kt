package com.pichurchyk.nutrition.di

import com.pichurchyk.nutrition.database.NutritionDao
import com.pichurchyk.nutrition.database.NutritionDatabase
import com.pichurchyk.nutrition.remote.source.NutritionRemoteDataSource
import com.pichurchyk.nutrition.repository.NutritionRepository
import com.pichurchyk.nutrition.repository.NutritionRepositoryImpl
import com.pichurchyk.nutrition.usecase.FetchRemoteAndLocalUseCase
import com.pichurchyk.nutrition.usecase.GetDailyInfoUseCase
import com.pichurchyk.nutrition.usecase.SaveIntakeUseCase
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
    single { GetDailyInfoUseCase(get()) }
    single { FetchRemoteAndLocalUseCase(get()) }
}

private fun provideNutritionDatabaseDao(database: NutritionDatabase): NutritionDao {
    return database.dao()
}
