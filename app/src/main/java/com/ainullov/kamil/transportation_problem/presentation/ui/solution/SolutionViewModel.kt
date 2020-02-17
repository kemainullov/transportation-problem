package com.ainullov.kamil.transportation_problem.presentation.ui.solution

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.domain.entities.NodeData
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.entities.Shipment
import com.ainullov.kamil.transportation_problem.domain.entities.TransportationProblemData
import com.ainullov.kamil.transportation_problem.domain.entities.state.State
import com.ainullov.kamil.transportation_problem.domain.interactors.SolutionInteractor
import com.ainullov.kamil.transportation_problem.presentation.base.BaseViewModel
import com.ainullov.kamil.transportation_problem.transportation_problem.TransportationProblem
import com.ainullov.kamil.transportation_problem.utils.extensions.default
import com.ainullov.kamil.transportation_problem.utils.extensions.set
import com.ainullov.kamil.transportation_problem.utils.getColumn
import com.ainullov.kamil.transportation_problem.utils.singletons.TransportationProblemSingleton
import de.blox.graphview.Graph
import de.blox.graphview.Node
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SolutionViewModel(private val solutionInteractor: SolutionInteractor) : BaseViewModel() {

    val state = MutableLiveData<State>().default(initialValue = State.Default())

    private fun saveProblemSolution(problemSolution: ProblemSolution) {
        solutionInteractor.insert(problemSolution)
    }

    fun updateProblemSolution(problemSolutionId: Long) {
        state.set(newValue = State.Loading())
        disposables.add(
            solutionInteractor.getSolutionById(problemSolutionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    TransportationProblemSingleton.updateTransportationProblemSingletonData(it)
                    state.set(newValue = State.Success(data = it))
                }, {
                    state.set(newValue = State.Error(message = "Error", errorCode = 1))
                })
        )
    }

    fun solveProblem(transportationProblemData: TransportationProblemData, method: Int) {
        state.set(newValue = State.Loading())
        disposables.add(
            TransportationProblem(transportationProblemData).execute(method)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ problemSolution ->
                    saveProblemSolution(problemSolution)
                    TransportationProblemSingleton.updateTransportationProblemSingletonData(
                        problemSolution
                    )
                    state.set(newValue = State.Success(data = problemSolution))
                }, {
                    state.set(newValue = State.Error(message = "Error", errorCode = 1))
                })
        )
    }

    fun prepareGraph(result: Array<Array<Shipment>>, resources: Resources): Graph {
        val resultGraph = Graph()
        val listOfSuppliers: MutableList<Node> = mutableListOf()
        val listOfConsumers: MutableList<Node> = mutableListOf()
        for (suppliers in TransportationProblemSingleton.transportationProblemData.supply.indices) {
            listOfSuppliers.add(
                Node(
                    NodeData(
                        resources.getString(
                            R.string.two_words_without_separating,
                            resources.getString(R.string.supplier_a),
                            (suppliers + 1).toString()
                        ),
                        true,
                        result[suppliers]
                    )
                )
            )
        }
        for (consumers in TransportationProblemSingleton.transportationProblemData.demand.indices) {
            listOfConsumers.add(
                Node(
                    NodeData(
                        resources.getString(
                            R.string.two_words_without_separating,
                            resources.getString(R.string.consumer_b),
                            (consumers + 1).toString()
                        ),
                        false,
                        getColumn(result, consumers)
                    )
                )
            )
        }

        for (row in 0 until listOfSuppliers.size) {
            for (column in 0 until listOfConsumers.size) {
                val shipment = result[row][column]
                if (shipment != TransportationProblem.ZERO && shipment.row == row && shipment.column == column) {
                    resultGraph.addEdge(listOfSuppliers[row], listOfConsumers[column])
                }
            }
        }
        return resultGraph
    }
}
