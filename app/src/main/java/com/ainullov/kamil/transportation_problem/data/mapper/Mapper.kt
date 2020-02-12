package com.ainullov.kamil.transportation_problem.data.mapper

interface Mapper<E, D> {

    fun mapFrom(from: E): D
    fun mapFrom(fromList: List<E>): List<D>
    fun mapTo(from: D): E
    fun mapTo(fromList: List<D>): List<E>

}