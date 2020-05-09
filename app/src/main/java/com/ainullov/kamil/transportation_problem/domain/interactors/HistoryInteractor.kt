package com.ainullov.kamil.transportation_problem.domain.interactors

import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.repository.SolutionRepository
import kotlinx.coroutines.flow.Flow

class HistoryInteractor(private val solutionRepository: SolutionRepository) {

    suspend fun getSolutionById(id: Long): ProblemSolution {
        return solutionRepository.getSolutionById(id)
    }

    fun getAllSolutions(): Flow<List<ProblemSolution>> {
        return solutionRepository.loadAll()
    }

    fun delete(problemSolution: ProblemSolution) {
        solutionRepository.delete(problemSolution)
    }

    fun deleteAll() {
        solutionRepository.deleteAll()
    }
}