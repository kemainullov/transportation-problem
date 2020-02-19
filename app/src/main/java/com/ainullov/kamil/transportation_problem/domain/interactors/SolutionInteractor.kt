package com.ainullov.kamil.transportation_problem.domain.interactors

import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.repository.SolutionRepository
import io.reactivex.Single

class SolutionInteractor(private val solutionRepository: SolutionRepository) {

    fun getSolutionById(id: Long): Single<ProblemSolution> {
        return solutionRepository.getSolutionById(id)
    }

    fun insert(problemSolution: ProblemSolution) {
        solutionRepository.insert(problemSolution)
    }
}