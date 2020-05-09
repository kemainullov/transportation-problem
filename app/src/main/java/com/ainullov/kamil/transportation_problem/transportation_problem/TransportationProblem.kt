package com.ainullov.kamil.transportation_problem.transportation_problem

import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.entities.Shipment
import com.ainullov.kamil.transportation_problem.domain.entities.TransportationProblemData
import com.ainullov.kamil.transportation_problem.utils.Const
import java.util.*

class TransportationProblem(
    private val transportationProblemData: TransportationProblemData
) {
    var supply: IntArray = transportationProblemData.supply
    var demand: IntArray = transportationProblemData.demand
    val costs: Array<DoubleArray> = transportationProblemData.costs
    private val balancedCosts: Array<DoubleArray>
    private var matrix: Array<Array<Shipment>>
    private var totalCosts = 0.0

    companion object {
        val ZERO = Shipment(0.0, 0.0, -1, -1) // to avoid nullable Shipments
    }

    init {
        val numSources = demand.size
        val numDestinations = demand.size
        val sources: MutableList<Int> = supply.toMutableList()
        val destinations: MutableList<Int> = demand.toMutableList()

        // fix imbalance
        val totalSources = sources.sum()
        val totalDestinations = destinations.sum()
        if (totalSources > totalDestinations)
            destinations.add(totalSources - totalDestinations)
        else if (totalDestinations > totalSources)
            sources.add(totalDestinations - totalSources)

        supply = sources.toIntArray()
        demand = destinations.toIntArray()

        balancedCosts = Array(supply.size) { DoubleArray(demand.size) }
        matrix = Array(supply.size) { Array(demand.size) { ZERO } }
        for (i in 0 until numSources)
            for (j in 0 until numDestinations)
                try {
                    balancedCosts[i][j] = costs[i][j]
                } catch (e: Exception) {
                }
    }

    private fun potentialMethod() {
        var maxReduction = 0.0
        var move: Array<Shipment>? = null
        var leaving = ZERO
        fixDegenerateCase()

        for (row in supply.indices) {
            for (column in demand.indices) {
                if (matrix[row][column] != ZERO) continue
                val trial = Shipment(0.0, balancedCosts[row][column], row, column)
                val path = getClosedPath(trial)
                var reduction = 0.0
                var lowestQuantity = Int.MAX_VALUE.toDouble()
                var leavingCandidate = ZERO
                var plus = true
                for (shipment in path) {
                    if (plus) {
                        reduction += shipment.costPerUnit
                    } else {
                        reduction -= shipment.costPerUnit
                        if (shipment.quantity < lowestQuantity) {
                            leavingCandidate = shipment
                            lowestQuantity = shipment.quantity
                        }
                    }
                    plus = !plus
                }
                if (reduction < maxReduction) {
                    move = path
                    leaving = leavingCandidate
                    maxReduction = reduction
                }
            }
        }

        if (move != null) {
            val quantity = leaving.quantity
            var plus = true
            for (shipment in move) {
                shipment.quantity += if (plus) quantity else -quantity
                println(shipment.quantity.toString())
                matrix[shipment.row][shipment.column] =
                    if (shipment.quantity == 0.0) ZERO else shipment
                plus = !plus
            }
            potentialMethod()
        }
    }

    private fun matrixToList() =
        LinkedList<Shipment>(matrix.flatten().filter { it != ZERO })

    private fun getClosedPath(shipment: Shipment): Array<Shipment> {
        val path = matrixToList()
        path.addFirst(shipment)
        // remove (and keep removing) elements that do not have a
        // vertical AND horizontal neighbor
        while (path.removeIf {
                val neighbors = getNeighbors(it, path)
                neighbors[0] == ZERO || neighbors[1] == ZERO
            }); // empty statement
        // place the remaining elements in the correct plus-minus order
        val stones = Array<Shipment>(path.size) { ZERO }
        var prev = shipment
        for (i in stones.indices) {
            stones[i] = prev
            prev = getNeighbors(prev, path)[i % 2]
        }
        return stones
    }

    private fun getNeighbors(shipment: Shipment, list: LinkedList<Shipment>): Array<Shipment> {
        val neighbors = Array<Shipment>(2) { ZERO }
        for (o in list) {
            if (o != shipment) {
                if (o.row == shipment.row && neighbors[0] == ZERO)
                    neighbors[0] = o
                else if (o.column == shipment.column && neighbors[1] == ZERO)
                    neighbors[1] = o
                if (neighbors[0] != ZERO && neighbors[1] != ZERO) break
            }
        }
        return neighbors
    }

    private fun fixDegenerateCase() {
        val eps = Double.MIN_VALUE
        if (supply.size + demand.size - 1 != matrixToList().size) {
            for (row in supply.indices) {
                for (column in demand.indices) {
                    if (matrix[row][column] == ZERO) {
                        val dummy = Shipment(eps, balancedCosts[row][column], row, column)
                        if (getClosedPath(dummy).isEmpty()) {
                            matrix[row][column] = dummy
                            return
                        }
                    }
                }
            }
        }
    }

    private fun printResult() {
        for (row in supply.indices) {
            for (column in demand.indices) {
                val shipment = matrix[row][column]
                if (shipment != ZERO && shipment.row == row && shipment.column == column) {
                    print(" %3s ".format(shipment.quantity.toInt()))
                    if (shipment.quantity.toInt() == 0)
                        matrix[row][column] = ZERO
                    totalCosts += shipment.quantity * shipment.costPerUnit
                } else print("  -  ")
            }
            println()
        }
        println("\nTotal costs: $totalCosts\n")
    }

    fun execute(method: Int): ProblemSolution {
        when (method) {
            Const.ReferencePlanMethods.NORTHWEST_CORNER -> matrix =
                NorthwestCornerRule(supply, demand, balancedCosts).northWestCornerRule()
            Const.ReferencePlanMethods.VOGELS_APPROXIMATION -> matrix =
                VogelApproximation(supply, demand, balancedCosts).vogelApproximation()
        }
        potentialMethod()
        printResult()

        return ProblemSolution(
            id = 0,
            transportationProblemData = transportationProblemData,
            minimumCosts = totalCosts.toInt(),
            matrix = matrix
        )
    }
}