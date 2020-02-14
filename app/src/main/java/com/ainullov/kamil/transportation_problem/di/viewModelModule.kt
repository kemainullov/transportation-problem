package com.ainullov.kamil.transportation_problem.di

import com.ainullov.kamil.transportation_problem.presentation.ui.history.HistoryViewModel
import com.ainullov.kamil.transportation_problem.presentation.ui.solution.SolutionViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { SolutionViewModel(get()) }
    viewModel { HistoryViewModel(get()) }
}