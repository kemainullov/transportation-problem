package com.ainullov.kamil.transportation_problem.utils.singletons

import com.ainullov.kamil.transportation_problem.domain.entities.TransportationProblemData
import com.ainullov.kamil.transportation_problem.presentation.base.App
import com.ainullov.kamil.transportation_problem.utils.Const

object TransportationProblemSingleton {
    init {
        initTransportationProblemData()
    }

    lateinit var transportationProblemData: TransportationProblemData

    private fun initTransportationProblemData(): TransportationProblemData {
        if (!::transportationProblemData.isInitialized)
            generateTransportationProblemData()
        return transportationProblemData
    }

    private fun generateTransportationProblemData() {
        transportationProblemData = TransportationProblemData(
            supply = App.transportationProblemSharedPreferences.getCustomIntArray(Const.PrefKeys.SUPPLY)
                ?: intArrayOf(),
            demand = App.transportationProblemSharedPreferences.getCustomIntArray(Const.PrefKeys.DEMAND)
                ?: intArrayOf(),
            costs = App.transportationProblemSharedPreferences.getCustomArrayOfDoubleArrays(Const.PrefKeys.COSTS)
                ?: arrayOf(
                    doubleArrayOf()
                )
        )
    }

    fun updateTransportationProblemData(transportationProblemData: TransportationProblemData) {
        App.transportationProblemSharedPreferences.setTransportationProblemData(transportationProblemData)
        generateTransportationProblemData()
    }

    fun removeTransportationProblemData(){
//        transportationProblemData = null
        App.transportationProblemSharedPreferences.removeTransportationProblemData()
    }

}