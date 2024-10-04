package com.pichurchyk.fitflow.di

import com.pichurchyk.fitflow.viewmodel.auth.AuthViewModel
import com.pichurchyk.fitflow.viewmodel.splash.SplashViewModel
import com.pichurchyk.fitflow.viewmodel.dashboard.DashboardViewModel
import com.pichurchyk.fitflow.viewmodel.addintake.AddIntakeViewModel
import com.pichurchyk.fitflow.viewmodel.waterintake.AddWaterIntakeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val mainModule = module {

    singleOf(::AuthViewModel)
    singleOf(::SplashViewModel)

    factoryOf(::DashboardViewModel)
    factoryOf(::AddIntakeViewModel)
    factoryOf(::AddWaterIntakeViewModel)

}