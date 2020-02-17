package com.ainullov.kamil.transportation_problem.presentation.ui.history

import androidx.lifecycle.MutableLiveData
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.entities.state.StateList
import com.ainullov.kamil.transportation_problem.domain.interactors.HistoryInteractor
import com.ainullov.kamil.transportation_problem.presentation.base.BaseViewModel
import com.ainullov.kamil.transportation_problem.utils.extensions.default
import com.ainullov.kamil.transportation_problem.utils.extensions.set
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HistoryViewModel(private val historyInteractor: HistoryInteractor) : BaseViewModel() {

    val state = MutableLiveData<StateList>().default(initialValue = StateList.Default())
    //state.postValue(State.Default())

    fun getAllProblemSolutions() {
        state.set(newValue = StateList.Loading())
        disposables.add(
            historyInteractor.getAllSolutions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    state.set(newValue = StateList.Success(data = it))
                }, {
                    state.set(newValue = StateList.Error(message = "Error", errorCode = 1))
                })
        )
    }

    fun delete(problemSolution: ProblemSolution) = historyInteractor.delete(problemSolution)

    fun deleteAll() = historyInteractor.deleteAll()
}