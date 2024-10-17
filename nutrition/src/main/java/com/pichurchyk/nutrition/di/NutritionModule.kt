package com.pichurchyk.nutrition.di

import com.pichurchyk.nutrition.repository.NutritionRepository
import com.pichurchyk.nutrition.database.NutritionDatabase
import com.pichurchyk.nutrition.database.repository.NutritionDatabaseRepositoryImpl
import com.pichurchyk.nutrition.remote.repository.NutritionRemoteRepositoryImpl
import com.pichurchyk.nutrition.remote.source.NutritionRemoteDataSource
import com.pichurchyk.nutrition.usecase.GetDailyInfoUseCase
import com.pichurchyk.nutrition.usecase.SaveIntakeUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val nutritionModule = module {


    single { NutritionRemoteDataSource(get()) }
    single { NutritionDatabase(get()) }
    single<NutritionRepository>(qualifier = named<Qualifiers.RemoteRepository>()) {
        NutritionRemoteRepositoryImpl(
            get()
        )
    }
    single<NutritionRepository>(qualifier = named<Qualifiers.DatabaseRepository>()) {
        provideNutritionDatabaseRepository(
            get()
        )
    }

//    Use Cases
    single { SaveIntakeUseCase(get(qualifier = named<Qualifiers.DatabaseRepository>())) }
    single { GetDailyInfoUseCase(get(qualifier = named<Qualifiers.DatabaseRepository>())) }

}

private fun provideNutritionDatabaseRepository(database: NutritionDatabase): NutritionRepository {
    return NutritionDatabaseRepositoryImpl(database.dao())
}
