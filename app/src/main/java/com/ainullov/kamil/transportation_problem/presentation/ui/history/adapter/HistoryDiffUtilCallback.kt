package com.ainullov.kamil.transportation_problem.presentation.ui.history.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution

class HistoryDiffUtilCallback internal constructor(
    private val oldList: List<ProblemSolution>,
    private val newList: List<ProblemSolution>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProblemSolution = oldList[oldItemPosition]
        val newProblemSolution = newList[newItemPosition]
        return oldProblemSolution.id == newProblemSolution.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldProblemSolution = oldList[oldItemPosition]
        val newProblemSolution = newList[newItemPosition]
        return newProblemSolution.id == oldProblemSolution.id &&
                newProblemSolution.minimumCosts == oldProblemSolution.minimumCosts &&
                newProblemSolution.transportationProblemData == oldProblemSolution.transportationProblemData
    }

}