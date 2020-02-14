package com.ainullov.kamil.transportation_problem.presentation.ui.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.presentation.ui.history.adapter.HistoryAdapter
import com.ainullov.kamil.transportation_problem.utils.adapter.ItemTouchHelperCallback
import com.ainullov.kamil.transportation_problem.utils.adapter.OnStartDragListener
import kotlinx.android.synthetic.main.activity_history.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivity : AppCompatActivity(), OnStartDragListener {

    private val viewModel: HistoryViewModel by viewModel<HistoryViewModel>()

    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var touchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        initHistoryRecycler()
        observeHistoryData()
    }

    private fun observeHistoryData() {

    }

    private fun initHistoryRecycler() {
        historyAdapter = HistoryAdapter(
            mutableListOf(),
            onClickListener = { quantity ->
                //                onConsumerItemClick(quantity)
            },
            onLongClickListener = { quantity ->
                //                onConsumerItemLongClick(quantity)
            },
            onDeleteClickListener = { position ->
                //                onConsumerItemDeleteClick(position)
            },
            onStartDragListener = this
        )
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

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
