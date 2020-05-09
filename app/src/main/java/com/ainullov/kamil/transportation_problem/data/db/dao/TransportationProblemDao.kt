package com.ainullov.kamil.transportation_problem.data.db.dao

import androidx.room.*
import com.ainullov.kamil.transportation_problem.data.models.ProblemSolutionModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TransportationProblemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(problemSolution: ProblemSolutionModel)

    @Query("SELECT * FROM Solution ORDER BY id DESC")
    fun loadAll(): Flow<List<ProblemSolutionModel>>

    @Delete
    fun delete(problemSolution: ProblemSolutionModel)

    @Query("DELETE FROM Solution")
    fun deleteAll()

    @Query("SELECT * FROM Solution where id = :id")
    suspend fun getSolutionById(id: Long): ProblemSolutionModel

    @Update
    fun update(problemSolution: ProblemSolutionModel)

}