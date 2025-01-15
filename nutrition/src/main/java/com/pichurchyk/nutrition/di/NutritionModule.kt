package com.pichurchyk.nutrition.di

import androidx.work.WorkManager
import com.pichurchyk.nutrition.database.NutritionDao
import com.pichurchyk.nutrition.database.NutritionDatabase
import com.pichurchyk.nutrition.remote.source.NutritionRemoteDataSource
import com.pichurchyk.nutrition.repository.NutritionRepository
import com.pichurchyk.nutrition.repository.NutritionRepositoryImpl
import com.pichurchyk.nutrition.usecase.GetDailyInfoUseCase
import com.pichurchyk.nutrition.usecase.SaveIntakeUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val nutritionModule = module {

    single { WorkManager.getInstance(androidContext()) }

    single { NutritionRemoteDataSource(get(), get()) }
    single { provideNutritionDatabaseDao(get()) }
    single { NutritionDatabase(get()) }
    single<NutritionRepository> {
        NutritionRepositoryImpl(
            localDataSource = get(),
        )
    }

//    Use Cases
    single { SaveIntakeUseCase(get()) }
    single { GetDailyInfoUseCase(get()) }
}

private fun provideNutritionDatabaseDao(database: NutritionDatabase): NutritionDao {
    return database.dao()
}
