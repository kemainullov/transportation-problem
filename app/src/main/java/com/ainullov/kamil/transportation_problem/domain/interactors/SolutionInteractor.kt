package com.ainullov.kamil.transportation_problem.domain.interactors

import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.repository.SolutionRepository

class SolutionInteractor(private val solutionRepository: SolutionRepository) {

    suspend fun getSolutionById(id: Long): ProblemSolution {
        return solutionRepository.getSolutionById(id)
    }

    fun insert(problemSolution: ProblemSolution) {
        solutionRepository.insert(problemSolution)
    }
}