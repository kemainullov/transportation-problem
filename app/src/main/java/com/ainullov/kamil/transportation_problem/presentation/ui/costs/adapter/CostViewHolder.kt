package com.ainullov.kamil.transportation_problem.presentation.ui.costs.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_costs.view.*

class CostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        position: Int,
//        quantity: Int,
        onClickListener: (Int) -> Unit,
        onLongClickListener: (Int) -> Unit
    ) {
        itemView.editText.hint = ""

        itemView.setOnClickListener { onClickListener(position) }
        itemView.setOnLongClickListener {
            onLongClickListener(position)
            true
        }
    }
}