package com.ainullov.kamil.transportation_problem.presentation.ui.consumers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.presentation.base.App
import com.ainullov.kamil.transportation_problem.presentation.ui.consumers.adapter.ConsumersAdapter
import com.ainullov.kamil.transportation_problem.utils.Const
import com.ainullov.kamil.transportation_problem.utils.adapter.ItemTouchHelperCallback
import com.ainullov.kamil.transportation_problem.utils.adapter.OnStartDragListener
import com.ainullov.kamil.transportation_problem.utils.dialogs.EditTextWithTwoButtonsAndTextViewDialog
import com.ainullov.kamil.transportation_problem.utils.dialogs.OnDialogResultListener
import com.ainullov.kamil.transportation_problem.utils.singletons.TransportationProblemSingleton
import kotlinx.android.synthetic.main.consumers_fragment.*

class ConsumersFragment : Fragment(), OnDialogResultListener, OnStartDragListener {

    companion object {
        fun newInstance() = ConsumersFragment()
    }

    private lateinit var viewModel: ConsumersViewModel
    private lateinit var consumersAdapter: ConsumersAdapter
    private lateinit var touchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.consumers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConsumersViewModel::class.java)
        setOnClickListeners()
        initConsumersRecycler()
    }

    override fun onPause() {
        super.onPause()
        if (!TransportationProblemSingleton.transportationProblemData.demand.contentEquals(
                consumersAdapter.list.toIntArray()
            )
        ) {
            TransportationProblemSingleton.transportationProblemData.demand =
                consumersAdapter.list.toIntArray()
            TransportationProblemSingleton.transportationProblemData.costs =
                arrayOf(doubleArrayOf())
        }
    }

    override fun onResume() {
        super.onResume()
        if (!TransportationProblemSingleton.transportationProblemData.demand.contentEquals(
                consumersAdapter.list.toIntArray()
            )
        )
            consumersAdapter.updateData(TransportationProblemSingleton.transportationProblemData.demand.toMutableList())
        checkForSuggestions()
    }

    private fun initConsumersRecycler() {
        consumersAdapter = ConsumersAdapter(
            TransportationProblemSingleton.transportationProblemData.demand.toMutableList(),
            onClickListener = { quantity ->
                onConsumerItemClick(quantity)
            },
            onLongClickListener = { quantity ->
                onConsumerItemLongClick(quantity)
            },
            onDeleteClickListener = { position ->
                onConsumerItemDeleteClick(position)
            },
            onStartDragListener = this
        )
        val callback: ItemTouchHelper.Callback =
            ItemTouchHelperCallback(
                consumersAdapter,
                isLongPressDragEnabled = true,
                isItemViewSwipeEnabled = true
            )
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(rv_consumers)
        rv_consumers.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_consumers.adapter = consumersAdapter
        checkForSuggestions()
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) =
        touchHelper.startDrag(viewHolder)

    private fun onConsumerItemClick(quantity: Int) {}

    private fun onConsumerItemLongClick(quantity: Int) {}

    private fun onConsumerItemDeleteClick(position: Int) {
        consumersAdapter.list.removeAt(position)
        consumersAdapter.notifyDataSetChanged()
        checkForSuggestions()
    }

    private fun setOnClickListeners() {
        floating_action_button.setOnClickListener {
            val dialog = EditTextWithTwoButtonsAndTextViewDialog.newInstance(
                title = getString(R.string.add_consumer),
                positiveBtnTitle = getString(R.string.add),
                negativeBtnTitle = getString(R.string.cancel),
                editTextHint = getString(R.string.enter_demand),
                onClickListener = {
                    when (it) {
                        EditTextWithTwoButtonsAndTextViewDialog.ACTION_POSITIVE -> {
                        }
                        EditTextWithTwoButtonsAndTextViewDialog.ACTION_NEGATIVE -> {
                        }
                    }
                }
            )
            dialog.show(childFragmentManager, null)
        }
    }

    override fun dialogResultEvent(result: Int) {
        consumersAdapter.list.add(result)
        consumersAdapter.notifyDataSetChanged()
        checkForSuggestions()
    }

    private fun checkForSuggestions() {
        if (!App.transportationProblemSharedPreferences.getCustomBoolean(Const.PrefKeys.DO_NOT_SHOW_HINTS))
            when (consumersAdapter.list.size) {
                0 -> {
                    cl_add_consumers_hint.visibility = View.VISIBLE
                    cl_delete_item_hint.visibility = View.GONE
                    cl_swipe_items_hint.visibility = View.GONE
                }
                1 -> {
                    cl_add_consumers_hint.visibility = View.GONE
                    cl_delete_item_hint.visibility = View.VISIBLE
                    cl_swipe_items_hint.visibility = View.GONE
                }
                2 -> {
                    cl_add_consumers_hint.visibility = View.GONE
                    cl_delete_item_hint.visibility = View.GONE
                    cl_swipe_items_hint.visibility = View.VISIBLE
                }
                else -> {
                    cl_add_consumers_hint.visibility = View.GONE
                    cl_delete_item_hint.visibility = View.GONE
                    cl_swipe_items_hint.visibility = View.GONE
                }
            }
        else {
            cl_add_consumers_hint.visibility = View.GONE
            cl_delete_item_hint.visibility = View.GONE
            cl_swipe_items_hint.visibility = View.GONE
        }
    }
}
