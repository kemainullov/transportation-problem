package com.ainullov.kamil.transportation_problem.domain.entities.state

sealed class StateList {
    class Default : StateList()
    class Loading : StateList()
    class Error<T>(val message: T, val errorCode: Int? = null) : StateList()
    class Success<T>(val data: List<T>) : StateList()
}