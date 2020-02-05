package com.ainullov.kamil.transportation_problem.domain.entities

data class Shipment(
    var quantity: Double,
    val costPerUnit: Double,
    val row: Int,
    val column: Int
)