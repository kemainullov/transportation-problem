package com.ainullov.kamil.transportation_problem.data.repository

import com.ainullov.kamil.transportation_problem.data.db.dao.TransportationProblemDao
import com.ainullov.kamil.transportation_problem.data.mapper.ProblemSolutionMapper
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.repository.SolutionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SolutionRepositoryImpl(
    private val transportationProblemDao: TransportationProblemDao,
    private val problemSolutionMapper: ProblemSolutionMapper
) : SolutionRepository {
    override fun insert(problemSolution: ProblemSolution) {
        return transportationProblemDao.insert(problemSolutionMapper.mapTo(problemSolution))
    }

    override fun loadAll(): Flow<List<ProblemSolution>> {
        return transportationProblemDao.loadAll()
            .map { problemSolutionMapper.mapFrom(it) }
    }

    override fun delete(problemSolution: ProblemSolution) {
        transportationProblemDao.delete(problemSolutionMapper.mapTo(problemSolution))
    }

    override fun deleteAll() {
        transportationProblemDao.deleteAll()
    }

    override suspend fun getSolutionById(id: Long): ProblemSolution =
        problemSolutionMapper.mapFrom(transportationProblemDao.getSolutionById(id))


    override fun update(problemSolution: ProblemSolution) {
        transportationProblemDao.update(problemSolutionMapper.mapTo(problemSolution))
    }
}


