package com.ainullov.kamil.transportation_problem.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ainullov.kamil.transportation_problem.data.db.dao.TransportationProblemDao
import com.ainullov.kamil.transportation_problem.data.models.ProblemSolutionModel

@Database(entities = [ProblemSolutionModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

//    abstract val transportationProblemDao: TransportationProblemDao
    abstract fun transportationProblemDao() : TransportationProblemDao

}