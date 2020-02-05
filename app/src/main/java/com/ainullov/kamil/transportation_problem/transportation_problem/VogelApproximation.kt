package com.ainullov.kamil.transportation_problem.transportation_problem

import com.ainullov.kamil.transportation_problem.domain.entities.Shipment

class VogelApproximation(
    private var supply: IntArray,
    private var demand: IntArray,
    private val costs: Array<DoubleArray>
) {
    private val nRows = supply.size
    private val nCols = demand.size
    private val rowDone = BooleanArray(nRows)
    private val colDone = BooleanArray(nCols)
    private val results = Array(nRows) { IntArray(nCols) }

    private fun nextCell(): IntArray {
        val res1 = maxPenalty(nRows, nCols, true)
        val res2 = maxPenalty(nCols, nRows, false)
        if (res1[3] == res2[3])
            return if (res1[2] < res2[2]) res1 else res2
        return if (res1[3] > res2[3]) res2 else res1
    }

    private fun diff(j: Int, len: Int, isRow: Boolean): IntArray {
        var min1 = Int.MAX_VALUE
        var min2 = min1
        var minP = -1
        for (i in 0 until len) {
            val done = if (isRow) colDone[i] else rowDone[i]
            if (done) continue
            val c = if (isRow) costs[j][i] else costs[i][j]
            if (c < min1) {
                min2 = min1
                min1 = c.toInt()
                minP = i
            } else if (c < min2) min2 = c.toInt()
        }
        return intArrayOf(min2 - min1, min1, minP)
    }

    private fun maxPenalty(length1: Int, length2: Int, isRow: Boolean): IntArray {
        var maxDiff = Int.MIN_VALUE
        var minCostPosition = -1
        var maxCostPosition = -1
        var minCost = -1
        for (i in 0 until length1) {
            val done = if (isRow) rowDone[i] else colDone[i]
            if (done) continue
            val res = diff(i, length2, isRow)
            if (res[0] > maxDiff) {
                maxDiff = res[0]  // max diff
                maxCostPosition = i  // pos of max diff
                minCost = res[1]  // min cost
                minCostPosition = res[2]  // pos of min cost
            }
        }
        return if (isRow) intArrayOf(maxCostPosition, minCostPosition, minCost, maxDiff) else
            intArrayOf(minCostPosition, maxCostPosition, minCost, maxDiff)
    }

    fun vogelApproximation(): Array<Array<Shipment>> {
        val matrix: Array<Array<Shipment>> =
            Array(supply.size) { Array(demand.size) { TransportationProblem.ZERO } }
        var supplyLeft = supply.sum()
        var totalCost = 0
        while (supplyLeft > 0) {
            val cell = nextCell()
            val row = cell[0]
            val column = cell[1]
            val quantity = minOf(demand[column], supply[row])
            demand[column] -= quantity
            if (demand[column] == 0) colDone[column] = true
            supply[row] -= quantity
            if (supply[row] == 0) rowDone[row] = true
            results[row][column] = quantity
            supplyLeft -= quantity
            totalCost += quantity * costs[row][column].toInt()

            matrix[row][column] = Shipment(quantity.toDouble(), costs[row][column], row, column)
        }
        return matrix
    }
}