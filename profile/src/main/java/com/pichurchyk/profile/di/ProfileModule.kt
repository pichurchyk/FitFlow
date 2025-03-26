package com.pichurchyk.profile.di

import com.pichurchyk.profile.data.repository.ProfileRepositoryImpl
import com.pichurchyk.profile.data.source.ProfileDataSource
import com.pichurchyk.profile.domain.repository.ProfileRepository
import com.pichurchyk.profile.ui.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val profileModule = module {
    viewModelOf(::ProfileViewModel)

    singleOf(::ProfileDataSource)
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }
}