package com.ainullov.kamil.transportation_problem.domain.entities

class TransportationProblemData(
    var supply: IntArray,
    var demand: IntArray,
    var costs: Array<DoubleArray>
){
    var matrix: Array<Array<Shipment>>? = null
    val isSolved: Boolean
    get() = !matrix.isNullOrEmpty()
}