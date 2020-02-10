package com.ainullov.kamil.transportation_problem.presentation.base

import android.app.Application
import android.content.Context
import com.ainullov.kamil.transportation_problem.utils.Preferences

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
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