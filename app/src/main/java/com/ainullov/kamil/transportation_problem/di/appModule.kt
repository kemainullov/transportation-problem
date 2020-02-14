package com.ainullov.kamil.transportation_problem.di

import android.app.Application
import android.content.Context
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val appModule = module {

    fun provideApplication(): Application = androidApplication()

    fun provideContext(): Context = androidContext()

    single { provideApplication() }
    single { provideContext() }
}