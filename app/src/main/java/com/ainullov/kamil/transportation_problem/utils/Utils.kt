package com.ainullov.kamil.transportation_problem.utils

import android.content.res.Resources
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.domain.entities.NodeData
import com.ainullov.kamil.transportation_problem.domain.entities.Shipment
import com.ainullov.kamil.transportation_problem.domain.entities.Solution
import com.ainullov.kamil.transportation_problem.transportation_problem.TransportationProblem
import java.lang.StringBuilder

fun getColumn(array: Array<Array<Shipment>>, index: Int): Array<Shipment?> {
    val column = arrayOfNulls<Shipment>(array[0].size - 1)
    for (i in column.indices)
        column[i] = array[i][index]
    return column
}

fun getSolutionDescriptionText(solution: Solution): String {
    val stringBuilder = StringBuilder()
    for (row in solution.transportationProblemData.supply.indices) {
        for (column in solution.transportationProblemData.demand.indices) {
            val shipment = solution.matrix[row][column]
            if (shipment != TransportationProblem.ZERO && shipment.row == row && shipment.column == column) {
                stringBuilder.append("A${row+1} -> B${column+1}: ${shipment.quantity.toInt()} ед. товара на сумму = ${shipment.quantity.toInt() * shipment.costPerUnit.toInt()}")
                stringBuilder.append("\n")
            }
        }
    }
    return stringBuilder.toString()
}


fun generateTextForGraphItem(nodeData: NodeData, resources: Resources): String {
    if (nodeData.isSupplier) {
        for (item in nodeData.array){
        }
    } else {
        for (item in nodeData.array){

        }
    }


    return ""
}

