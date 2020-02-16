package com.ainullov.kamil.transportation_problem.presentation.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.utils.adapter.ItemTouchHelperAdapter

class HistoryAdapter(
    var list: MutableList<ProblemSolution>,
    private val onClickListener: (ProblemSolution) -> Unit,
    private val onLongClickListener: (ProblemSolution) -> Unit,
    private val onDeleteClickListener: (ProblemSolution) -> Unit
) : RecyclerView.Adapter<HistoryViewHolder>(), ItemTouchHelperAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_history, parent, false
        )
        return HistoryViewHolder(itemView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(
            problemSolution = list[position],
            onClickListener = onClickListener,
            onLongClickListener = onLongClickListener,
            onDeleteClickListener = onDeleteClickListener
        )
    }

    fun updateData(list: MutableList<ProblemSolution>) {
        val diffResult = DiffUtil.calculateDiff(HistoryDiffUtilCallback(this.list, list))
        diffResult.dispatchUpdatesTo(this)
        this.list = list
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        return true
    }

    override fun onItemDismiss(position: Int) {
        onDeleteClickListener(list[position])
    }

    override fun onItemSelected(viewHolder: RecyclerView.ViewHolder?) {
    }

    override fun onItemClear(viewHolder: RecyclerView.ViewHolder) {
        notifyDataSetChanged()
    }
}
