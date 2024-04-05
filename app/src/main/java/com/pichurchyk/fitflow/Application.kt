package com.pichurchyk.fitflow

import android.app.Application
import android.content.Context
import com.pichurchyk.fitflow.di.initKoin
import org.koin.dsl.module


class Application : Application() {
    private var appContext: Context? = null


    override fun onCreate() {
        super.onCreate()
        appContext = this

        initKoin(androidModule)
    }

    private val androidModule = module {}
}