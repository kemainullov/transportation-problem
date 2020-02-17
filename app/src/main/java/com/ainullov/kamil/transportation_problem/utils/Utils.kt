package com.ainullov.kamil.transportation_problem.utils

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.domain.entities.NodeData
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.entities.Shipment
import com.ainullov.kamil.transportation_problem.transportation_problem.TransportationProblem

fun getColumn(array: Array<Array<Shipment>>, index: Int): Array<Shipment> {
    val column =
        try {
            arrayOfNulls<Shipment>(array[0].size)
        } catch (e: Exception) {
            arrayOfNulls<Shipment>(array[0].size - 1)
        }
    for (i in column.indices) {
        column[i] = array[i][index]
    }
    return column as Array<Shipment>
}

fun getSolutionDescriptionText(problemSolution: ProblemSolution, resources: Resources): String {
    val stringBuilder = StringBuilder()
    for (row in problemSolution.transportationProblemData.supply.indices) {
        for (column in problemSolution.transportationProblemData.demand.indices) {
            val shipment = problemSolution.matrix[row][column]
            if (shipment != TransportationProblem.ZERO && shipment.row == row && shipment.column == column) {
                stringBuilder.append(resources.getString(R.string.supplier_a))
                stringBuilder.append("${row + 1} ")
                stringBuilder.append("${resources.getString(R.string.transport_to)} ")
                stringBuilder.append("${resources.getString(R.string.consumer_b_diff_ru_ending)}${column + 1}:\n")
                stringBuilder.append("${shipment.quantity.toInt()} ")
                stringBuilder.append("${resources.getString(R.string.units_amount_of_goods)} ")
                stringBuilder.append("${shipment.quantity.toInt() * shipment.costPerUnit.toInt()}")
                stringBuilder.append("\n\n")
            }
        }
    }
    return stringBuilder.toString()
}


fun generateTextForGraphItem(nodeData: NodeData, resources: Resources): String {
    val stringBuilder = StringBuilder("")
    var counter = 1
    if (nodeData.isSupplier) {
        for (item in nodeData.array.filter { it.quantity.toInt() != 0 }) {
            stringBuilder.append("${counter}) ")
            stringBuilder.append("${item.quantity.toInt()} ")
            stringBuilder.append("${resources.getString(R.string.units_amount_of_goods)} ")
            stringBuilder.append("${item.quantity.toInt() * item.costPerUnit.toInt()} ")
            stringBuilder.append("${resources.getString(R.string.consumer_b_diff_ru_ending)}${item.column + 1}.\n")
            counter++
        }
    } else {
        for (item in nodeData.array.filter { it.quantity.toInt() != 0 }) {
            stringBuilder.append("${counter}) ")
            stringBuilder.append("${item.quantity.toInt()} ")
            stringBuilder.append("${resources.getString(R.string.units_amount_of_goods)} ")
            stringBuilder.append("${item.quantity.toInt() * item.costPerUnit.toInt()} ")
            stringBuilder.append("${resources.getString(R.string.from_supplier_a)}${item.row + 1}.\n")
            counter++
        }
    }
    return stringBuilder.toString()
}

fun showKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
}

fun hideKeyboard(context: Context, view: View) {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

