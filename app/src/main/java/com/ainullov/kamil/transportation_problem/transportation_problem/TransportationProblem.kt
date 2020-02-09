package com.ainullov.kamil.transportation_problem.transportation_problem

import com.ainullov.kamil.transportation_problem.domain.entities.Shipment
import java.io.File
import java.util.Scanner
import java.util.LinkedList

class TransportationProblem(
    var supply: IntArray,
    var demand: IntArray,
    val initialCosts: Array<DoubleArray>
) {
    private val costs: Array<DoubleArray>
    private var matrix: Array<Array<Shipment>>

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
        println(sources.toString())
        println(destinations.toString())

        supply = sources.toIntArray()
        demand = destinations.toIntArray()

        costs = Array(supply.size) { DoubleArray(demand.size) }
        matrix = Array(supply.size) { Array(demand.size) { ZERO } }
        for (i in 0 until numSources)
            for (j in 0 until numDestinations)
                try {
                    costs[i][j] = initialCosts[i][j]
                } catch (e: Exception) {
                }
    }

    private fun potentialMethod() {
        var maxReduction = 0.0
        var move: Array<Shipment>? = null
        var leaving = ZERO
        fixDegenerateCase()

        for (row in 0 until supply.size) {
            for (column in 0 until demand.size) {
                if (matrix[row][column] != ZERO) continue
                val trial = Shipment(0.0, costs[row][column], row, column)
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
        for (i in 0 until stones.size) {
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
            for (row in 0 until supply.size) {
                for (column in 0 until demand.size) {
                    if (matrix[row][column] == ZERO) {
                        val dummy = Shipment(eps, costs[row][column], row, column)
                        if (getClosedPath(dummy).size == 0) {
                            matrix[row][column] = dummy
                            return
                        }
                    }
                }
            }
        }
    }

    private fun printResult() {
//        val text = File(filename).readText()
//        println("$filename\n\n$text")
//        println("Optimal solution $filename\n")
        var totalCosts = 0.0

        for (row in 0 until supply.size) {
            for (column in 0 until demand.size) {
                val shipment = matrix[row][column]
                if (shipment != ZERO && shipment.row == row && shipment.column == column) {
                    print(" %3s ".format(shipment.quantity.toInt()))
                    totalCosts += shipment.quantity * shipment.costPerUnit
                } else print("  -  ")
            }
            println()
        }
        println("\nTotal costs: $totalCosts\n")
    }

    fun execute(method: Int) {
        when (method) {
            1 -> matrix = NorthwestCornerRule(supply, demand, costs).northWestCornerRule()
            2 -> matrix = VogelApproximation(supply, demand, costs).vogelApproximation()
        }
        potentialMethod()
        printResult()
    }
}