package com.pichurchyk.profile.data.source

import com.pichurchyk.profile.data.model.response.UserParamsResponse
import com.pichurchyk.profile.data.source.resource.UserParamsResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileDataSource(
    private val httpClient: HttpClient
) {

    suspend fun getUserParams(): Flow<UserParamsResponse> = flow {
        httpClient
            .get(UserParamsResource())
            .body<List<UserParamsResponse>>()
            .also { emit(it.first()) }
    }
}