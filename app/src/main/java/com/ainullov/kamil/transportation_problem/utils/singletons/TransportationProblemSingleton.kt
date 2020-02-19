package com.ainullov.kamil.transportation_problem.utils.singletons

import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.entities.TransportationProblemData
import com.ainullov.kamil.transportation_problem.presentation.base.App
import com.ainullov.kamil.transportation_problem.utils.Const

object TransportationProblemSingleton {
    init {
        initTransportationProblemSingletonData()
    }

    lateinit var transportationProblemData: TransportationProblemData
    var problemSolutionId: Long = -1

    private fun initTransportationProblemSingletonData(): TransportationProblemData {
        if (!::transportationProblemData.isInitialized)
            generateTransportationProblemSingletonData()
        return transportationProblemData
    }

    private fun generateTransportationProblemSingletonData() {
        transportationProblemData = TransportationProblemData(
            supply = App.transportationProblemSharedPreferences.getCustomIntArray(Const.PrefKeys.SUPPLY)
                ?: intArrayOf(),
            demand = App.transportationProblemSharedPreferences.getCustomIntArray(Const.PrefKeys.DEMAND)
                ?: intArrayOf(),
            costs = App.transportationProblemSharedPreferences.getCustomArrayOfDoubleArrays(Const.PrefKeys.COSTS)
                ?: arrayOf(doubleArrayOf())
        )
        problemSolutionId =
            App.transportationProblemSharedPreferences.getCustomLong(Const.PrefKeys.PROBLEM_SOLUTION_ID)
    }

    fun updateTransportationProblemSingletonData(problemSolution: ProblemSolution) {
        App.transportationProblemSharedPreferences.setTransportationProblemData(problemSolution.transportationProblemData)
        App.transportationProblemSharedPreferences.setCustomLong(
            Const.PrefKeys.PROBLEM_SOLUTION_ID,
            problemSolution.id
        )
        generateTransportationProblemSingletonData()
    }

    fun removeTransportationProblemSingletonData() {
        App.transportationProblemSharedPreferences.removeTransportationProblemData()
        App.transportationProblemSharedPreferences.removeCurrentSolutionId()

        transportationProblemData = TransportationProblemData(
            supply = intArrayOf(),
            demand = intArrayOf(),
            costs = arrayOf(doubleArrayOf())
        )
        problemSolutionId = -1

    }

}