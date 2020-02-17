package com.ainullov.kamil.transportation_problem.presentation.ui.history

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.domain.entities.ProblemSolution
import com.ainullov.kamil.transportation_problem.domain.entities.state.StateList
import com.ainullov.kamil.transportation_problem.presentation.ui.history.adapter.HistoryAdapter
import com.ainullov.kamil.transportation_problem.utils.Const
import com.ainullov.kamil.transportation_problem.utils.adapter.ItemTouchHelperCallback
import com.ainullov.kamil.transportation_problem.utils.extensions.typeOf
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, HistoryActivity::class.java)
            return intent
        }
    }

    private val viewModel: HistoryViewModel by viewModel<HistoryViewModel>()

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var touchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        initHistoryRecycler()
        setOnClickListeners()
        observeStates()
        lifecycle.addObserver(viewModel)
        viewModel.getAllProblemSolutions()
    }

    private fun observeStates() {
        viewModel.state.observe(this, Observer<StateList> { state ->
            when (state) {
                is StateList.Default -> {
                }
                is StateList.Loading -> {
                }
                is StateList.Success<*> -> {
                    if (state.data.isNotEmpty())
                        when (state.data[0]) {
                            is ProblemSolution -> historyAdapter.updateData(state.data.typeOf<ProblemSolution>().toMutableList())
                        }
                }
                is StateList.Error<*> -> {
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

    private fun initHistoryRecycler() {
        historyAdapter = HistoryAdapter(
            mutableListOf(),
            onClickListener = { problemSolution ->
                onHistoryItemClick(problemSolution)
            },
            onLongClickListener = { problemSolution ->
                onHistoryItemLongClick(problemSolution)
            },
            onDeleteClickListener = { problemSolution ->
                onHistoryItemDeleteClick(problemSolution)
            })
        val callback: ItemTouchHelper.Callback = ItemTouchHelperCallback(
            historyAdapter,
            isLongPressDragEnabled = false,
            isItemViewSwipeEnabled = true
        )
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(rv_history)

        rv_history.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_history.adapter = historyAdapter
    }

    private fun onHistoryItemClick(problemSolution: ProblemSolution) {
        setResult(
            Activity.RESULT_OK,
            Intent().putExtra(Const.Extras.SOLUTION_ID, problemSolution.id)
        )
        finish()
    }

    private fun onHistoryItemLongClick(problemSolution: ProblemSolution) {
        setResult(
            Activity.RESULT_OK,
            Intent().putExtra(Const.Extras.SOLUTION_ID, problemSolution.id)
        )
        finish()
    }

    private fun onHistoryItemDeleteClick(problemSolution: ProblemSolution) {
        viewModel.delete(problemSolution)
        historyAdapter.list.remove(problemSolution)
        historyAdapter.notifyDataSetChanged()
    }

    private fun setOnClickListeners() {
        ib_back.setOnClickListener { onBackPressed() }
        ll_delete_all.setOnClickListener {
            viewModel.deleteAll()
            historyAdapter.updateData(mutableListOf())
        }
    }

}
