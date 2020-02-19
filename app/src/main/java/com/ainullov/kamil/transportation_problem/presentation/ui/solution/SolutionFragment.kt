package com.ainullov.kamil.transportation_problem.presentation.ui.solution

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.entities.Shipment
import com.ainullov.kamil.transportation_problem.domain.entities.TransportationProblemData
import com.ainullov.kamil.transportation_problem.domain.entities.state.State
import com.ainullov.kamil.transportation_problem.presentation.base.App
import com.ainullov.kamil.transportation_problem.presentation.ui.solution.graph.GraphAdapter
import com.ainullov.kamil.transportation_problem.utils.Const
import com.ainullov.kamil.transportation_problem.utils.getSolutionDescriptionText
import com.ainullov.kamil.transportation_problem.utils.singletons.TransportationProblemSingleton
import com.airbnb.lottie.LottieDrawable
import de.blox.graphview.Graph
import de.blox.graphview.energy.FruchtermanReingoldAlgorithm
import kotlinx.android.synthetic.main.solution_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SolutionFragment : Fragment() {

    companion object {
        fun newInstance() = SolutionFragment()
    }

    private val viewModel: SolutionViewModel by viewModel<SolutionViewModel>()
    private var method = Const.ReferencePlanMethods.VOGELS_APPROXIMATION
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
        initAnimationListeners()
        lifecycle.addObserver(viewModel)
        observeStates()
    }

    private fun observeStates() {
        viewModel.state.observe(this, Observer<State> { state ->
            when (state) {
                is State.Default -> {
                }
                is State.Loading -> {
                    onStateLoading()
                }
                is State.Success<*> -> {
                    when (state.data) {
                        is ProblemSolution -> {
                            TransportationProblemSingleton.updateTransportationProblemSingletonData(
                                state.data
                            )
                            onStateSuccess(state.data)
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

    private fun onStateLoading() {
        cl_anim.visibility = View.VISIBLE
        loading_animation.visibility = View.VISIBLE
        loading_animation.progress = 0F
        loading_animation.pauseAnimation()
        loading_animation.playAnimation()
        loading_animation.repeatMode = LottieDrawable.RESTART
    }

    private fun onStateSuccess(data: ProblemSolution) {
        problemSolution = data
        check_animation.visibility = View.VISIBLE
        loading_animation.visibility = View.GONE
        check_animation.progress = 0F
        check_animation.pauseAnimation()
        check_animation.playAnimation()
    }

    override fun onResume() {
        super.onResume()
        checkResultVisibility()
        checkForSuggestions()
    }

    private fun initAnimationListeners() {
        check_animation.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {
                cl_anim.visibility = View.GONE
                check_animation.visibility = View.GONE
                prepareDisplay(problemSolution)
            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {}
        })
    }

    private fun initCheckBoxChangeListener() {
        mb_northwest_corner.setOnCheckedChangeListener { _, b ->
            if (b) mb_vogels_approximation.isChecked = false
            method = Const.ReferencePlanMethods.NORTHWEST_CORNER
        }
        mb_vogels_approximation.setOnCheckedChangeListener { _, b ->
            if (b) mb_northwest_corner.isChecked = false
            method = Const.ReferencePlanMethods.VOGELS_APPROXIMATION
        }
    }

    private fun setOnClickListeners() {
        btn_solve.setOnClickListener { onBtnSolveClicked() }
    }

    private fun onBtnSolveClicked() {
        if (problemDataValidation(TransportationProblemSingleton.transportationProblemData))
            viewModel.solveProblem(TransportationProblemSingleton.transportationProblemData, method)
        else Toast.makeText(
            activity ?: App.appContext,
            resources.getString(R.string.incorrect_data),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun problemDataValidation(transportationProblemData: TransportationProblemData): Boolean {
        if (transportationProblemData.supply.isEmpty()
            || transportationProblemData.demand.isEmpty()
            || (transportationProblemData.costs.contentEquals(arrayOf(doubleArrayOf())))
        ) return false
        return true
    }

    private fun prepareDisplay(problemSolution: ProblemSolution) {
        if (problemSolution.minimumCosts != 0)
            tv_minimum_costs.text = getString(
                R.string.two_words_separated_by_colon,
                getString(R.string.minimum_costs),
                problemSolution.minimumCosts.toString()
            )
        tv_solution_description.visibility = View.VISIBLE
        tv_solution_description.text = getSolutionDescriptionText(problemSolution, resources)
        cl_result.visibility = View.VISIBLE
        checkForSuggestions()
        drawResultGraph(problemSolution.matrix)
    }

    private fun initGraphAdapter() {
        val graph = Graph()
        val adapter = GraphAdapter(graph,
            onClickListener = {},
            onLongClickListener = {})
        gv_graph.adapter = adapter
        gv_graph.setOnTouchListener { _, _ ->
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
        viewModel.updateProblemSolution(solutionId)
    }

    private fun checkResultVisibility() {
        if (::problemSolution.isInitialized && TransportationProblemSingleton.transportationProblemData == problemSolution.transportationProblemData) {
            cl_result.visibility = View.VISIBLE
            tv_solution_description.visibility = View.VISIBLE
        } else {
            cl_result.visibility = View.GONE
            tv_solution_description.visibility = View.GONE
            tv_minimum_costs.text = ""
        }
    }

    private fun checkForSuggestions() {
        if (!App.transportationProblemSharedPreferences.getCustomBoolean("do_not_show_hints"))
            cl_get_solution_hint.visibility =
                if (cl_result.visibility == View.GONE) View.VISIBLE else View.GONE
        else cl_get_solution_hint.visibility = View.GONE
    }
}
