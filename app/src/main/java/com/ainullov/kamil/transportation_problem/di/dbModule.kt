package com.ainullov.kamil.transportation_problem.di

import android.app.Application
import androidx.room.Room
import com.ainullov.kamil.transportation_problem.data.db.AppDatabase
import com.ainullov.kamil.transportation_problem.data.db.dao.TransportationProblemDao
import org.koin.dsl.module.module

val dbModule = module {

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "transportation_problem_database"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    fun provideDao(database: AppDatabase): TransportationProblemDao =
        database.transportationProblemDao()

//    single { provideDatabase(androidApplication()) }
    single { provideDatabase(get()) }
    single { provideDao(get()) }
}