package com.ainullov.kamil.transportation_problem.transportation_problem

import com.ainullov.kamil.transportation_problem.domain.entities.Shipment

class NorthwestCornerRule(
    private var supply: IntArray,
    private var demand: IntArray,
    private val costs: Array<DoubleArray>
) {
    private val matrix: Array<Array<Shipment>> =
        Array(supply.size) { Array(demand.size) { TransportationProblem.ZERO } }

    fun northWestCornerRule(): Array<Array<Shipment>> {
        var northwest = 0
        for (row in 0 until supply.size)
            for (column in northwest until demand.size) {
                val quantity = minOf(supply[row], demand[column]).toDouble()
                if (quantity > 0.0) {
                    matrix[row][column] = Shipment(quantity, costs[row][column], row, column)
                    supply[row] -= quantity.toInt()
                    demand[column] -= quantity.toInt()
                    if (supply[row] == 0) {
                        northwest = column
                        break
                    }
                }
            }
        return matrix
    }
}