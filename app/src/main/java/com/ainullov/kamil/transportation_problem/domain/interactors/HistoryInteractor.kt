package com.ainullov.kamil.transportation_problem.domain.interactors

import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.repository.SolutionRepository
import io.reactivex.Flowable
import io.reactivex.Single

class HistoryInteractor(private val solutionRepository: SolutionRepository) {

    fun getSolutionById(id: Long): Single<ProblemSolution> {
        return solutionRepository.getSolutionById(id)
    }

    fun getAllSolutions(): Flowable<List<ProblemSolution>> {
        return solutionRepository.loadAll()
    }

    fun delete(problemSolution: ProblemSolution) {
        solutionRepository.delete(problemSolution)
    }

    fun deleteAll() {
        solutionRepository.deleteAll()
    }
}