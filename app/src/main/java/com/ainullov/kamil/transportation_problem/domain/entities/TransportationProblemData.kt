package com.ainullov.kamil.transportation_problem.domain.entities

data class TransportationProblemData(
    var supply: IntArray,
    var demand: IntArray,
    var costs: Array<DoubleArray>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TransportationProblemData

        if (!supply.contentEquals(other.supply)) return false
        if (!demand.contentEquals(other.demand)) return false
        if (!costs.contentDeepEquals(other.costs)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = supply.contentHashCode()
        result = 31 * result + demand.contentHashCode()
        result = 31 * result + costs.contentDeepHashCode()
        return result
    }
}