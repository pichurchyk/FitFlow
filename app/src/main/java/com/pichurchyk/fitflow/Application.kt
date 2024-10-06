package com.pichurchyk.fitflow

import android.app.Application
import android.content.Context
import com.pichurchyk.fitflow.auth.di.authModule
import com.pichurchyk.fitflow.di.initKoin
import com.pichurchyk.fitflow.di.mainModule
import com.pichurchyk.nutrition.database.di.nutritionDatabaseModule
import com.pichurchyk.supabase.di.supabaseModule
import org.koin.dsl.module

class Application : Application() {
    private var appContext: Context? = null


    override fun onCreate() {
        super.onCreate()
        appContext = this

        initKoin(
            mainModule,
            androidModule,
            authModule,
            supabaseModule,
            nutritionDatabaseModule,
        )
    }

    private val androidModule = module {
        single { this@Application.appContext }
    }
}