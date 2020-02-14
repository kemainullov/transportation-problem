package com.ainullov.kamil.transportation_problem.presentation.ui.costs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.presentation.ui.costs.adapter.CostsAdapter
import com.ainullov.kamil.transportation_problem.utils.singletons.TransportationProblemSingleton
import kotlinx.android.synthetic.main.costs_fragment.*


class CostsFragment : Fragment() {

    companion object {
        fun newInstance() = CostsFragment()
    }

    private lateinit var viewModel: CostsViewModel
    private lateinit var costsAdapter: CostsAdapter
    lateinit var consumers: IntArray
    lateinit var suppliers: IntArray

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.costs_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CostsViewModel::class.java)
        consumers = TransportationProblemSingleton.transportationProblemData.demand
        suppliers = TransportationProblemSingleton.transportationProblemData.supply
        initCostsRecycler()
        fillMatrix()
    }

    override fun onResume() {
        super.onResume()
        checkSituation()
    }

    override fun onPause() {
        super.onPause()
        saveCostsMatrix()
    }

    private fun saveCostsMatrix() {
        if (suppliers.isNotEmpty() && consumers.isNotEmpty()) {
            val costs: Array<DoubleArray> = Array(suppliers.size) { DoubleArray(consumers.size) }
            var counter = 0
            for (i in 0 until suppliers.size) {
                for (j in 0 until consumers.size) {
                    costs[i][j] = costsAdapter.list[counter].toDouble()
                    counter++
                }
            }
            TransportationProblemSingleton.transportationProblemData.costs = costs
        }
    }

    private fun initCostsRecycler() {
        costsAdapter = CostsAdapter(
            MutableList<Int>(consumers.size * suppliers.size) { 0 },
            onClickListener = { quantity ->
            },
            onLongClickListener = { quantity ->
            },
            onItemCostChangeListener = { position, cost ->
                costsAdapter.list[position] = cost
            })

        rv_costs.layoutManager =
            GridLayoutManager(activity, if (consumers.isNotEmpty()) consumers.size else 1)
        rv_costs.adapter = costsAdapter
    }

    private fun checkSituation() {
        if (suppliers.size != TransportationProblemSingleton.transportationProblemData.supply.size || consumers.size != TransportationProblemSingleton.transportationProblemData.demand.size) {
            if (TransportationProblemSingleton.transportationProblemData.supply.isNotEmpty() && TransportationProblemSingleton.transportationProblemData.demand.isNotEmpty())
                loadMatrix()
        }
    }

    private fun loadMatrix() {
        suppliers = TransportationProblemSingleton.transportationProblemData.supply
        consumers = TransportationProblemSingleton.transportationProblemData.demand
        initCostsRecycler()
    }

    private fun fillMatrix() {
        if (suppliers.size == TransportationProblemSingleton.transportationProblemData.supply.size && consumers.size == TransportationProblemSingleton.transportationProblemData.demand.size) {
            if (TransportationProblemSingleton.transportationProblemData.costs.isNotEmpty()) {
                val list = mutableListOf<Int>()
                for (i in TransportationProblemSingleton.transportationProblemData.costs) {
                    for (j in i) {
                        list.add(j.toInt())
                    }
                }
                costsAdapter.updateData(list)
            }
        }
    }


}
