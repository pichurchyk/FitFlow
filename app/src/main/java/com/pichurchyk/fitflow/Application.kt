package com.pichurchyk.fitflow

import android.app.Application
import android.content.Context
import com.pichurchyk.fitflow.di.initKoin
import com.pichurchyk.nutrition.database.di.nutritionDatabaseModule
import org.koin.dsl.module


class Application : Application() {
    private var appContext: Context? = null


    override fun onCreate() {
        super.onCreate()
        appContext = this

        initKoin(
            androidModule,
            nutritionDatabaseModule
        )
    }

    private val androidModule = module {
        single { this@Application.appContext }
    }
}