package com.pichurchyk.fitflow.common.di

import com.pichurchyk.fitflow.common.preferences.AuthPreferences
import com.pichurchyk.fitflow.common.preferences.AuthPreferencesActions
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val commonModule = module {
    single<AuthPreferencesActions> {
        AuthPreferences(androidContext())
    }
}