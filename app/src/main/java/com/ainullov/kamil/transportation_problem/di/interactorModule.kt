package com.ainullov.kamil.transportation_problem.di

import com.ainullov.kamil.transportation_problem.domain.interactors.HistoryInteractor
import com.ainullov.kamil.transportation_problem.domain.interactors.SolutionInteractor
import com.ainullov.kamil.transportation_problem.domain.repository.SolutionRepository
import org.koin.dsl.module.module

val interactorModule = module {

    fun provideSolutionInteractor(
        solutionRepository: SolutionRepository
    ): SolutionInteractor = SolutionInteractor(solutionRepository)

    fun provideHistoryInteractor(
        solutionRepository: SolutionRepository
    ): HistoryInteractor = HistoryInteractor(solutionRepository)

    single { provideSolutionInteractor(get()) }
    single { provideHistoryInteractor(get()) }
}