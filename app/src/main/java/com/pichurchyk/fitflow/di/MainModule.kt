package com.pichurchyk.fitflow.di

import com.pichurchyk.fitflow.viewmodel.auth.AuthViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val mainModule = module {

    singleOf(::AuthViewModel)

}