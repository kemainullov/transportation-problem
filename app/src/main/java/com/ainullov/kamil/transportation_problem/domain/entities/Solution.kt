package com.ainullov.kamil.transportation_problem.domain.entities

class Solution(
    val id: Long,
    var transportationProblemData: TransportationProblemData,
    var minimumCosts: Int,
    var matrix: Array<Array<Shipment>>)