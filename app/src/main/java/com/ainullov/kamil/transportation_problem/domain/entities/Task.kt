package com.ainullov.kamil.transportation_problem.domain.entities

class Task(
    var supply: IntArray,
    var demand: IntArray,
    val initialCosts: Array<DoubleArray>,
    val isSolved: Boolean,
    var matrix: Array<Array<Shipment>>
)