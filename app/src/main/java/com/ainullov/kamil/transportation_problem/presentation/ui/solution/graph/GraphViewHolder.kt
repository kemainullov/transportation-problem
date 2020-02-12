package com.ainullov.kamil.transportation_problem.presentation.ui.solution.graph

import android.view.View
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.domain.entities.NodeData
import com.ainullov.kamil.transportation_problem.utils.dialogs.OnClickGraphItemDialog
import com.ainullov.kamil.transportation_problem.utils.generateTextForGraphItem
import de.blox.graphview.Graph
import de.blox.graphview.ViewHolder
import kotlinx.android.synthetic.main.item_node.view.*

class GraphViewHolder(itemView: View) : ViewHolder(itemView) {

    fun bind(
        graph: Graph,
        position: Int,
        onClickListener: (Int) -> Unit,
        onLongClickListener: (Int) -> Unit
    ) {
        if (graph.getNode(position).data is NodeData) {
            val nodeData = graph.getNode(position).data as NodeData

            itemView.tv_node.text = nodeData.text
            itemView.setOnClickListener {
                OnClickGraphItemDialog(
                    itemView.context,
                    "${nodeData.text} ${if (nodeData.isSupplier) itemView.resources.getString(R.string.transports_colon) else itemView.resources.getString(R.string.expects_colon)}\n" +
                            generateTextForGraphItem(nodeData, itemView.resources)
                ).show()


                onClickListener(1)
            }
            itemView.setOnLongClickListener {
                onLongClickListener(1)
                true
            }
        }


    }

}