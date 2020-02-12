package com.ainullov.kamil.transportation_problem.data.mapper

import com.ainullov.kamil.transportation_problem.data.models.ProblemSolutionModel
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution

class ProblemSolutionMapper : Mapper<ProblemSolutionModel, ProblemSolution> {

    override fun mapFrom(fromList: List<ProblemSolutionModel>): List<ProblemSolution> {
        return fromList.map { (mapFrom(it)) }
    }

    override fun mapFrom(from: ProblemSolutionModel): ProblemSolution {
        return ProblemSolution(
            id = from.id,
            transportationProblemData = from.transportationProblemData,
            minimumCosts = from.minimumCosts,
            matrix = from.matrix
        )
    }

    override fun mapTo(fromList: List<ProblemSolution>): List<ProblemSolutionModel> {
        return fromList.map { (mapTo(it)) }
    }

    override fun mapTo(from: ProblemSolution): ProblemSolutionModel {
        return ProblemSolutionModel(
            id = from.id,
            transportationProblemData = from.transportationProblemData,
            minimumCosts = from.minimumCosts,
            matrix = from.matrix
        )
    }
}