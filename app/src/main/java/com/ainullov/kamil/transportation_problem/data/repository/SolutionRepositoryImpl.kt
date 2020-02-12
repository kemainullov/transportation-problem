package com.ainullov.kamil.transportation_problem.data.repository

import com.ainullov.kamil.transportation_problem.data.db.dao.TransportationProblemDao
import com.ainullov.kamil.transportation_problem.data.mapper.ProblemSolutionMapper
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.repository.SolutionRepository
import io.reactivex.Flowable
import io.reactivex.Single

class SolutionRepositoryImpl(
    private val transportationProblemDao: TransportationProblemDao,
    private val problemSolutionMapper: ProblemSolutionMapper
) : SolutionRepository {
    override fun insert(problemSolution: ProblemSolution) {
        return transportationProblemDao.insert(problemSolutionMapper.mapTo(problemSolution))
    }

    override fun loadAll(): Flowable<List<ProblemSolution>> {
        return transportationProblemDao.loadAll()
            .map { problemSolutionMapper.mapFrom(it) }
    }

    override fun delete(problemSolution: ProblemSolution) {
        transportationProblemDao.delete(problemSolutionMapper.mapTo(problemSolution))
    }

    override fun deleteAll() {
        transportationProblemDao.deleteAll()
    }

    override fun getSolutionById(id: Long): Single<ProblemSolution> {
        return transportationProblemDao.getSolutionById(id)
            .map { problemSolutionMapper.mapFrom(it) }
    }

    override fun update(problemSolution: ProblemSolution) {
        transportationProblemDao.update(problemSolutionMapper.mapTo(problemSolution))
    }
}


