package com.ainullov.kamil.transportation_problem.domain.repository

import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import io.reactivex.Flowable
import io.reactivex.Single

interface SolutionRepository {

    fun insert(problemSolution: ProblemSolution)

    fun loadAll(): Flowable<List<ProblemSolution>>

    fun delete(problemSolution: ProblemSolution)

    fun deleteAll()

    fun getSolutionById(id: Long): Single<ProblemSolution>

    fun update(problemSolution: ProblemSolution)
}