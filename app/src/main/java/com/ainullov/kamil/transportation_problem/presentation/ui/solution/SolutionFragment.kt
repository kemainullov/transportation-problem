package com.ainullov.kamil.transportation_problem.presentation.ui.solution

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.entities.Shipment
import com.ainullov.kamil.transportation_problem.domain.entities.state.State
import com.ainullov.kamil.transportation_problem.presentation.ui.solution.graph.GraphAdapter
import com.ainullov.kamil.transportation_problem.transportation_problem.TransportationProblem
import com.ainullov.kamil.transportation_problem.utils.Const
import com.ainullov.kamil.transportation_problem.utils.getSolutionDescriptionText
import com.ainullov.kamil.transportation_problem.utils.singletons.TransportationProblemSingleton
import de.blox.graphview.Graph
import de.blox.graphview.energy.FruchtermanReingoldAlgorithm
import kotlinx.android.synthetic.main.solution_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SolutionFragment : Fragment() {

    companion object {
        fun newInstance() = SolutionFragment()
    }

    private val viewModel: SolutionViewModel by viewModel<SolutionViewModel>()
    private var method = Const.ReferencePlanMethods.NORTHWEST_CORNER
    private lateinit var problemSolution: ProblemSolution

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.solution_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initCheckBoxChangeListener()
        setOnClickListeners()
        initGraphAdapter()
        lifecycle.addObserver(viewModel)
        observeStates()

    }

    private fun observeStates() {
        viewModel.state.observe(this, Observer<State> { state ->
            when (state) {
                is State.Default -> {
                }
                is State.Loading -> {
                }
                is State.Success<*> -> {
                    when (state.data) {
                        is ProblemSolution -> {
                            problemSolution =
                                TransportationProblem(TransportationProblemSingleton.transportationProblemData).execute(
                                    method
                                )
                            prepareDisplay(problemSolution)
                        }
                    }
                }
                is State.Error<*> -> {
                    when (state.message) {
                        is Int -> {
                        }
                        is String -> {
                        }
                    }
                }
            }

        })
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
            onBtnSolveClicked()
        }
    }

    private fun onBtnSolveClicked() {
        problemSolution =
            TransportationProblem(TransportationProblemSingleton.transportationProblemData).execute(
                method
            )
        prepareDisplay(problemSolution)
        viewModel.saveProblemSolution(problemSolution)
    }

    private fun prepareDisplay(problemSolution: ProblemSolution){
        tv_minimum_costs.visibility = View.VISIBLE
        tv_minimum_costs.text = getString(
            R.string.two_words_separated_by_colon,
            getString(R.string.minimum_costs),
            problemSolution.minimumCosts.toString()
        )
        tv_solution_description.visibility = View.VISIBLE
        tv_solution_description.text = getSolutionDescriptionText(problemSolution, resources)
        drawResultGraph(problemSolution.matrix)
        cl_result.visibility = View.VISIBLE
        TransportationProblemSingleton.updateTransportationProblemSingletonData(problemSolution)
    }


    private fun initGraphAdapter() {
        val graph = Graph()
        val adapter = GraphAdapter(graph,
            onClickListener = {},
            onLongClickListener = {})
        gv_graph.adapter = adapter
        gv_graph.setOnTouchListener { v, event ->
            sv_solution.requestDisallowInterceptTouchEvent(true)
            false
        }
        adapter.algorithm = FruchtermanReingoldAlgorithm(1000)
    }

    private fun drawResultGraph(result: Array<Array<Shipment>>) {
        val resultGraph = viewModel.prepareGraph(result, resources)
        val adapter = GraphAdapter(resultGraph,
            onClickListener = {},
            onLongClickListener = {})
        gv_graph.adapter = adapter
        adapter.algorithm = FruchtermanReingoldAlgorithm(1000)
    }

    fun onProblemSolutionChanged(solutionId: Long) {
        viewModel.updateProblemSolution(
            solutionId
        )
    }
}
