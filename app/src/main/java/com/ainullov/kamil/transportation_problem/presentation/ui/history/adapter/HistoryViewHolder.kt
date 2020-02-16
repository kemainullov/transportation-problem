package com.ainullov.kamil.transportation_problem.presentation.ui.history.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import kotlinx.android.synthetic.main.item_history.view.*

class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        problemSolution: ProblemSolution,
        onClickListener: (ProblemSolution) -> Unit,
        onLongClickListener: (ProblemSolution) -> Unit,
        onDeleteClickListener: (ProblemSolution) -> Unit
    ) {
        itemView.tv_suppliers_quantity.text = itemView.context.resources.getString(
            R.string.two_words_separated_by_equals_sign_and_ending_with_colon,
            itemView.context.resources.getString(R.string.suppliers_quantity),
            problemSolution.transportationProblemData.costs.size.toString()
        )
        itemView.tv_suppliers_data.text =
            problemSolution.transportationProblemData.supply.toString()
        itemView.tv_consumers_quantity.text = itemView.context.resources.getString(
            R.string.two_words_separated_by_equals_sign_and_ending_with_colon,
            itemView.context.resources.getString(R.string.consumers_quantity),
            problemSolution.transportationProblemData.demand.size.toString()
        )
        itemView.tv_consumers_data.text =
            problemSolution.transportationProblemData.demand.toString()
        itemView.tv_minimum_costs.text = itemView.context.resources.getString(
            R.string.two_words_separated_by_colon,
            itemView.context.resources.getString(R.string.minimum_costs),
            problemSolution.minimumCosts.toString()
        )

        itemView.iv_delete.setOnClickListener { onDeleteClickListener(problemSolution) }
        itemView.cl_content.setOnClickListener { onClickListener(problemSolution) }
        itemView.cl_content.setOnLongClickListener {
            onLongClickListener(problemSolution)
            true
        }
    }
}