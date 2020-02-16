package com.ainullov.kamil.transportation_problem.data.db.dao

import androidx.room.*
import com.ainullov.kamil.transportation_problem.data.models.ProblemSolutionModel
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TransportationProblemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(problemSolution: ProblemSolutionModel)

    @Query("SELECT * FROM Solution ORDER BY id DESC")
    fun loadAll(): Flowable<List<ProblemSolutionModel>>

    @Delete
    fun delete(problemSolution: ProblemSolutionModel)

    @Query("DELETE FROM Solution")
    fun deleteAll()

    @Query("SELECT * FROM Solution where id = :id")
    fun getSolutionById(id: Long): Single<ProblemSolutionModel>

    @Update
    fun update(problemSolution: ProblemSolutionModel)

}