package com.ainullov.kamil.transportation_problem.presentation.ui.costs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ainullov.kamil.transportation_problem.R

class CostsAdapter(
    var list: MutableList<Int>,
    private val onClickListener: (Int) -> Unit,
    private val onLongClickListener: (Int) -> Unit
) : RecyclerView.Adapter<CostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_costs, parent, false
        )
        return CostViewHolder(itemView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CostViewHolder, position: Int) {
        holder.bind(
            position = position,
//            quantity = list[position],
            onClickListener = onClickListener,
            onLongClickListener = onLongClickListener
        )
    }

    fun updateData(list: MutableList<Int>) {
        this.list = list
    }
}