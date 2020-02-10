package com.ainullov.kamil.transportation_problem.presentation.ui.costs.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ainullov.kamil.transportation_problem.utils.singletons.TransportationProblemSingleton
import kotlinx.android.synthetic.main.item_costs.view.*

class CostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        position: Int,
        onClickListener: (Int) -> Unit,
        onLongClickListener: (Int) -> Unit,
        onItemCostChangeListener: (Int, Int) -> Unit
    ) {
        val demands = TransportationProblemSingleton.transportationProblemData.demand
        itemView.editText.hint =
            "C[${position / demands.size + 1}][${position - position / demands.size * demands.size + 1}]"

        itemView.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                onItemCostChangeListener(
                    position,
                    if (p0.toString() != "") p0.toString().toInt() else 0
                )
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        itemView.setOnClickListener { onClickListener(position) }
        itemView.setOnLongClickListener {
            onLongClickListener(position)
            true
        }
    }
}