package com.pichurchyk.profile.data.repository

import com.pichurchyk.profile.data.source.ProfileDataSource
import com.pichurchyk.profile.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val dataSource: ProfileDataSource
) : ProfileRepository {



}