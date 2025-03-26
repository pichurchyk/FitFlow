package com.pichurchyk.fitflow.di

import com.pichurchyk.fitflow.viewmodel.auth.AuthViewModel
import com.pichurchyk.fitflow.viewmodel.splash.SplashViewModel
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardViewModel
import com.pichurchyk.fitflow.viewmodel.addintake.AddIntakeViewModel
import com.pichurchyk.fitflow.viewmodel.waterintake.AddWaterIntakeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val mainModule = module {

    singleOf(::AuthViewModel)
    singleOf(::SplashViewModel)

    viewModelOf(::DashboardViewModel)
    viewModelOf(::AddIntakeViewModel)
    viewModelOf(::AddWaterIntakeViewModel)
    viewModelOf(::AddWaterIntakeViewModel)

}