package com.ainullov.kamil.transportation_problem.presentation.ui.solution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.domain.entities.Shipment
import com.ainullov.kamil.transportation_problem.presentation.ui.solution.graph.GraphAdapter
import com.ainullov.kamil.transportation_problem.transportation_problem.TransportationProblem
import com.ainullov.kamil.transportation_problem.utils.Const
import com.ainullov.kamil.transportation_problem.utils.singletons.TransportationProblemSingleton
import de.blox.graphview.Graph
import de.blox.graphview.Node
import de.blox.graphview.energy.FruchtermanReingoldAlgorithm
import kotlinx.android.synthetic.main.solution_fragment.*


class SolutionFragment : Fragment() {

    companion object {
        fun newInstance() = SolutionFragment()
    }

    private lateinit var viewModel: SolutionViewModel
    private var method = Const.ReferencePlanMethods.NORTHWEST_CORNER
    private lateinit var result: Array<Array<Shipment>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.solution_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SolutionViewModel::class.java)
        initCheckBoxChangeListener()
        setOnClickListeners()
        initGraphAdapter()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun initCheckBoxChangeListener() {
        mb_northwest_corner.setOnCheckedChangeListener { compoundButton, b ->
            if (b) mb_vogels_approximation.isChecked = false
            method = Const.ReferencePlanMethods.NORTHWEST_CORNER
        }
        mb_vogels_approximation.setOnCheckedChangeListener { compoundButton, b ->
            if (b) mb_northwest_corner.isChecked = false
            method = Const.ReferencePlanMethods.VOGELS_APPROXIMATION

        }
    }

    private fun setOnClickListeners() {
        btn_solve.setOnClickListener {
            result =
                TransportationProblem(TransportationProblemSingleton.transportationProblemData).execute(
                    method
                )
            drawResultGraph(result)
        }
    }

    private fun initGraphAdapter() { // Иначе вылетает, посмотреть что можно сделать
        val graph = Graph()
        val adapter = GraphAdapter(graph,
            onClickListener = {},
            onLongClickListener = {})
        gv_graph.adapter = adapter
        adapter.algorithm = FruchtermanReingoldAlgorithm(1000)
    }

    private fun drawResultGraph(result: Array<Array<Shipment>>) {
        val resultGraph = Graph()
        val listOfSuppliers: MutableList<Node> = mutableListOf()
        val listOfConsumers: MutableList<Node> = mutableListOf()

        for (suppliers in TransportationProblemSingleton.transportationProblemData.supply.indices) {
            listOfSuppliers.add(
                Node(
                    getString(
                        R.string.two_words_without_separating,
                        getString(R.string.supplier_a),
                        (suppliers + 1).toString()
                    )
                )
            )
        }
        for (consumers in TransportationProblemSingleton.transportationProblemData.demand.indices) {
            listOfConsumers.add(
                Node(
                    getString(
                        R.string.two_words_without_separating,
                        getString(R.string.consumer_b),
                        (consumers + 1).toString()
                    )
                )
            )
        }


//        var totalCosts = 0.0

        for (row in 0 until listOfSuppliers.size) {
            for (column in 0 until listOfConsumers.size) {
                val shipment = result[row][column]
                if (shipment != TransportationProblem.ZERO && shipment.row == row && shipment.column == column) {
//                    totalCosts += shipment.quantity * shipment.costPerUnit
                    resultGraph.addEdge(listOfSuppliers[row], listOfConsumers[column])
                }
            }
        }
        val adapter = GraphAdapter(resultGraph,
            onClickListener = {},
            onLongClickListener = {})
        gv_graph.adapter = adapter
        adapter.algorithm = FruchtermanReingoldAlgorithm(1000)
    }
}
