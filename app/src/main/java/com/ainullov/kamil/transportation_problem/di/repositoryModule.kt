package com.ainullov.kamil.transportation_problem.di

import com.ainullov.kamil.transportation_problem.data.db.dao.TransportationProblemDao
import com.ainullov.kamil.transportation_problem.data.mapper.ProblemSolutionMapper
import com.ainullov.kamil.transportation_problem.data.repository.SolutionRepositoryImpl
import com.ainullov.kamil.transportation_problem.domain.repository.SolutionRepository
import org.koin.dsl.module.module

val repositoryModule = module {

    fun provideSolutionRepository(
        transportationProblemDao: TransportationProblemDao,
        problemSolutionMapper: ProblemSolutionMapper
    ): SolutionRepository = SolutionRepositoryImpl(transportationProblemDao, problemSolutionMapper)

    single { provideSolutionRepository(get(), get()) }
}