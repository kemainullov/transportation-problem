package com.ainullov.kamil.transportation_problem.utils.extensions

//Reified list of type * to type T
inline fun <reified T> List<*>.typeOf(): List<T> {
    val list = mutableListOf<T>()
    this.forEach {
        if (it is T) {
            list.add(it)
        }
    }
    return list
}