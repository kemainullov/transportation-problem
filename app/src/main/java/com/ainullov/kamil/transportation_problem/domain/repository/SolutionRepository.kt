package com.ainullov.kamil.transportation_problem.domain.repository

import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import kotlinx.coroutines.flow.Flow

interface SolutionRepository {

    fun insert(problemSolution: ProblemSolution)

    fun loadAll(): Flow<List<ProblemSolution>>

    fun delete(problemSolution: ProblemSolution)

    fun deleteAll()

    suspend fun getSolutionById(id: Long): ProblemSolution

    fun update(problemSolution: ProblemSolution)
}