package com.ainullov.kamil.transportation_problem.presentation.ui.costs

import androidx.lifecycle.ViewModel
import com.ainullov.kamil.transportation_problem.utils.singletons.TransportationProblemSingleton

class CostsViewModel : ViewModel() {

    fun saveCostsMatrix(suppliers: IntArray, consumers: IntArray, listOfCosts: List<Int>) {
        if (suppliers.isNotEmpty() && consumers.isNotEmpty()) {
            val costs: Array<DoubleArray> = Array(suppliers.size) { DoubleArray(consumers.size) }
            var counter = 0
            for (i in suppliers.indices)
                for (j in consumers.indices) {
                    costs[i][j] = listOfCosts[counter].toDouble()
                    counter++
                }
            TransportationProblemSingleton.transportationProblemData.costs = costs
        }
    }

}
