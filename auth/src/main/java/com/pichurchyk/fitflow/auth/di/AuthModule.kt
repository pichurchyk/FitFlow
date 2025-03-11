package com.pichurchyk.fitflow.auth.di

import com.pichurchyk.fitflow.auth.datasource.AuthDataSource
import com.pichurchyk.fitflow.auth.repository.AuthRepository
import com.pichurchyk.fitflow.auth.repository.AuthRepositoryImpl
import com.pichurchyk.fitflow.auth.usecase.GetSignedInUserImpl
import com.pichurchyk.fitflow.auth.usecase.GetSignedInUserUseCase
import com.pichurchyk.fitflow.auth.usecase.SignInUseCase
import com.pichurchyk.fitflow.auth.usecase.SignInUseCaseImpl
import com.pichurchyk.fitflow.auth.usecase.SignOutUseCase
import com.pichurchyk.fitflow.auth.usecase.SignOutUseCaseImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authModule = module {

    singleOf(::AuthDataSource)
    single<AuthRepository> { AuthRepositoryImpl(get()) }

//    Use Cases
    single<SignInUseCase> { SignInUseCaseImpl(get()) }
    single<GetSignedInUserUseCase> { GetSignedInUserImpl(get()) }
    single<SignOutUseCase> { SignOutUseCaseImpl(get()) }
}