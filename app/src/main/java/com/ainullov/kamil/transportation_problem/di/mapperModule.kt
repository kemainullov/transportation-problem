package com.ainullov.kamil.transportation_problem.di

import com.ainullov.kamil.transportation_problem.data.mapper.ProblemSolutionMapper
import org.koin.dsl.module.module

val mapperModule = module {

    fun provideProblemSolutionMapper(
    ): ProblemSolutionMapper = ProblemSolutionMapper()

    single { provideProblemSolutionMapper() }
}