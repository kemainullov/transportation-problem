package com.ainullov.kamil.transportation_problem.domain.entities.state

sealed class State {
    class Default : State()
    class Loading : State()
    class Error<T>(val message: T, val errorCode: Int? = null) : State()
    class Success<T>(val data: T) : State()
}