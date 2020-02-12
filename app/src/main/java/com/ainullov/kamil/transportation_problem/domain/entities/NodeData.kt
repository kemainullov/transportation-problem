package com.ainullov.kamil.transportation_problem.domain.entities

data class NodeData(val text: String, val isSupplier: Boolean, val array: Array<Shipment>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NodeData

        if (text != other.text) return false
        if (isSupplier != other.isSupplier) return false
        if (!array.contentEquals(other.array)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + isSupplier.hashCode()
        result = 31 * result + array.contentHashCode()
        return result
    }
}