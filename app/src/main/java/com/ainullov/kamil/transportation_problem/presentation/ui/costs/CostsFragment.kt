package com.ainullov.kamil.transportation_problem.presentation.ui.costs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.presentation.base.App
import com.ainullov.kamil.transportation_problem.presentation.ui.costs.adapter.CostsAdapter
import com.ainullov.kamil.transportation_problem.utils.Const
import com.ainullov.kamil.transportation_problem.utils.singletons.TransportationProblemSingleton
import kotlinx.android.synthetic.main.costs_fragment.*


class CostsFragment : Fragment() {

    companion object {
        fun newInstance() = CostsFragment()
    }

    private lateinit var viewModel: CostsViewModel
    private lateinit var costsAdapter: CostsAdapter
    private lateinit var consumers: IntArray
    private lateinit var suppliers: IntArray
    private lateinit var costs: Array<DoubleArray>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.costs_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CostsViewModel::class.java)
        initCostsRecycler()
        fillMatrix()
    }

    override fun onResume() {
        super.onResume()
        checkSituation()
        checkForSuggestions()
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveCostsMatrix(suppliers, consumers, costsAdapter.list)
    }

    private fun initCostsRecycler() {
        consumers = TransportationProblemSingleton.transportationProblemData.demand
        suppliers = TransportationProblemSingleton.transportationProblemData.supply
        costs = TransportationProblemSingleton.transportationProblemData.costs
        costsAdapter = CostsAdapter(
            MutableList(consumers.size * suppliers.size) { 0 },
            onClickListener = { },
            onLongClickListener = { },
            onItemCostChangeListener = { position, cost ->
                costsAdapter.list[position] = cost
            })

        tv_horizontal.visibility =
            if (consumers.isNotEmpty() && suppliers.isNotEmpty()) View.VISIBLE else View.GONE
        tv_vertical.visibility =
            if (consumers.isNotEmpty() && suppliers.isNotEmpty()) View.VISIBLE else View.GONE
        rv_costs.layoutManager =
            GridLayoutManager(activity, if (consumers.isNotEmpty()) consumers.size else 1)
        rv_costs.adapter = costsAdapter
    }

    private fun checkSituation() {
        if (suppliers.size != TransportationProblemSingleton.transportationProblemData.supply.size || consumers.size != TransportationProblemSingleton.transportationProblemData.demand.size) {
            if (TransportationProblemSingleton.transportationProblemData.supply.isNotEmpty() && TransportationProblemSingleton.transportationProblemData.demand.isNotEmpty()) {
                loadMatrix()
                fillMatrix()
            } else loadMatrix()
        }
    }

    private fun loadMatrix() {
        initCostsRecycler()
    }

    private fun fillMatrix() {
        if (suppliers.size == TransportationProblemSingleton.transportationProblemData.supply.size && consumers.size == TransportationProblemSingleton.transportationProblemData.demand.size) {
            if (TransportationProblemSingleton.transportationProblemData.costs.isNotEmpty() && costs.isNotEmpty() && costs.contentDeepEquals(
                    TransportationProblemSingleton.transportationProblemData.costs
                )
            ) {
                val list = mutableListOf<Int>()
                for (i in TransportationProblemSingleton.transportationProblemData.costs)
                    for (j in i)
                        list.add(j.toInt())
                if (list.isNotEmpty())
                    costsAdapter.updateData(list)
            }
        }
    }

    private fun checkForSuggestions() {
        if (!App.transportationProblemSharedPreferences.getCustomBoolean(Const.PrefKeys.DO_NOT_SHOW_HINTS))
            when (costsAdapter.list.size) {
                0 -> {
                    cl_fill_costs_hint.visibility = View.VISIBLE
                }
                else -> cl_fill_costs_hint.visibility = View.GONE
            }
        else cl_fill_costs_hint.visibility = View.GONE
    }
}
