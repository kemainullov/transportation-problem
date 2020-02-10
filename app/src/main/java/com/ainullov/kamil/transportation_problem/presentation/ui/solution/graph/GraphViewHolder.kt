package com.ainullov.kamil.transportation_problem.presentation.ui.solution.graph

import android.view.View
import android.widget.TextView
import com.ainullov.kamil.transportation_problem.R
import de.blox.graphview.Graph
import de.blox.graphview.ViewHolder
import kotlinx.android.synthetic.main.item_node.view.*

class GraphViewHolder(itemView: View) : ViewHolder(itemView) {

    fun bind(
        graph: Graph,
        position: Int,
        text: String,
        onClickListener: (Int) -> Unit,
        onLongClickListener: (Int) -> Unit
    ) {
        itemView.textView.text = if(text.isNotEmpty()) text else "1"
        itemView.setOnClickListener { onClickListener(1) }
        itemView.setOnLongClickListener {
            onLongClickListener(1)
            true
        }
    }

}