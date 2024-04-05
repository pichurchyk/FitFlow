package com.pichurchyk.fitflow.di

import com.pichurchyk.fitflow.data.repository.FitnessRepositoryImpl
import com.pichurchyk.fitflow.data.source.fitness.FitnessLocalSource
import com.pichurchyk.fitflow.data.source.fitness.FitnessRemoteSource
import org.koin.dsl.module

val fitnessModule = module {

    single {
        FitnessRemoteSource(httpClient = get())
    }

    single {
        FitnessLocalSource()
    }

    single {
        FitnessRepositoryImpl(remoteSource = get(), localSource = get())
    }

}