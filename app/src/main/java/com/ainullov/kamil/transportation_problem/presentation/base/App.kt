package com.ainullov.kamil.transportation_problem.presentation.base

import android.app.Application
import android.content.Context
import com.ainullov.kamil.transportation_problem.di.*
import com.ainullov.kamil.transportation_problem.utils.Preferences
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        startKoin(
            this,
            listOf(
                appModule,
                dbModule,
                interactorModule,
                mapperModule,
                repositoryModule,
                viewModelModule
            )
        )
    }

    companion object {
        lateinit var appContext: Context
        private var appPreferences: Preferences? = null

        val transportationProblemSharedPreferences: Preferences
            get() {
                val preferences = appPreferences ?: Preferences(appContext)
                if (appPreferences == null)
                    appPreferences = preferences
                return preferences
            }
    }
}