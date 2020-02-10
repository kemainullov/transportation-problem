package com.ainullov.kamil.transportation_problem.presentation.ui.solution.graph

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ainullov.kamil.transportation_problem.R
import de.blox.graphview.BaseGraphAdapter
import de.blox.graphview.Graph


class GraphAdapter(
    graph: Graph,
    private val graphForAdapter: Graph = graph,
    private val onClickListener: (Int) -> Unit,
    private val onLongClickListener: (Int) -> Unit): BaseGraphAdapter<GraphViewHolder>(graph) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GraphViewHolder {
        val view: View = LayoutInflater.from(parent!!.context).inflate(R.layout.item_node, parent, false)
        return GraphViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: GraphViewHolder, data: Any?, position: Int) {
//        (viewHolder as GraphViewHolder).textView.setText(attr.data.toString())
        viewHolder.bind(graph = graphForAdapter,
            position = position,
            text = graphForAdapter.getNode(position).data.toString(), //сделать
            onClickListener = onClickListener,
            onLongClickListener = onLongClickListener)
    }
}