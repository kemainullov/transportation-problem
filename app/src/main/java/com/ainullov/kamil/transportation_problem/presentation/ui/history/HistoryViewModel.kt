package com.ainullov.kamil.transportation_problem.presentation.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.entities.state.StateList
import com.ainullov.kamil.transportation_problem.domain.interactors.HistoryInteractor
import com.ainullov.kamil.transportation_problem.presentation.base.BaseViewModel
import com.ainullov.kamil.transportation_problem.utils.Const
import com.ainullov.kamil.transportation_problem.utils.extensions.default
import com.ainullov.kamil.transportation_problem.utils.extensions.post
import com.ainullov.kamil.transportation_problem.utils.extensions.set
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel(private val historyInteractor: HistoryInteractor) : BaseViewModel() {

    val state = MutableLiveData<StateList>().default(initialValue = StateList.Default())

    fun getAllProblemSolutions() {
        state.set(newValue = StateList.Loading())
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    historyInteractor.getAllSolutions().collect {
                        state.post(newValue = StateList.Success(data = it))
                    }
                } catch (e: Exception){
                    state.post(newValue = StateList.Error(message = R.string.error, errorCode = Const.ErrorCode.ERROR))
                }

            }
        }
    }

    fun delete(problemSolution: ProblemSolution) = historyInteractor.delete(problemSolution)

    fun deleteAll() = historyInteractor.deleteAll()
}