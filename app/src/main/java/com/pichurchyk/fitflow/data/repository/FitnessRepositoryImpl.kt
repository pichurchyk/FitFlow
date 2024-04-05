package com.pichurchyk.fitflow.data.repository

import com.pichurchyk.fitflow.data.source.fitness.FitnessLocalSource
import com.pichurchyk.fitflow.data.source.fitness.FitnessRemoteSource
import com.pichurchyk.fitflow.domain.repository.FitnessRepository

class FitnessRepositoryImpl(
    val remoteSource: FitnessRemoteSource,
    val localSource: FitnessLocalSource
) : FitnessRepository {

}