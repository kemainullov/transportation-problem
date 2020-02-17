package com.ainullov.kamil.transportation_problem.presentation.ui.suppliers

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
import com.ainullov.kamil.transportation_problem.presentation.ui.suppliers.adapter.SuppliersAdapter
import com.ainullov.kamil.transportation_problem.utils.adapter.ItemTouchHelperCallback
import com.ainullov.kamil.transportation_problem.utils.adapter.OnStartDragListener
import com.ainullov.kamil.transportation_problem.utils.dialogs.EditTextWithTwoButtonsAndTextViewDialog
import com.ainullov.kamil.transportation_problem.utils.dialogs.OnDialogResultListener
import com.ainullov.kamil.transportation_problem.utils.singletons.TransportationProblemSingleton
import kotlinx.android.synthetic.main.suppliers_fragment.*


class SuppliersFragment : Fragment(), OnDialogResultListener,
    OnStartDragListener {

    companion object {
        fun newInstance() = SuppliersFragment()
    }

    private lateinit var viewModel: SuppliersViewModel
    private lateinit var suppliersAdapter: SuppliersAdapter
    private lateinit var touchHelper: ItemTouchHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.suppliers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SuppliersViewModel::class.java)
        setOnClickListeners()
        initSuppliersRecycler()
    }

    override fun onPause() {
        super.onPause()
        if (!TransportationProblemSingleton.transportationProblemData.supply.contentEquals(suppliersAdapter.list.toIntArray())) {
            TransportationProblemSingleton.transportationProblemData.supply = suppliersAdapter.list.toIntArray()
            TransportationProblemSingleton.transportationProblemData.costs = arrayOf(doubleArrayOf())
        }
    }

    override fun onResume() {
        super.onResume()
        if (!TransportationProblemSingleton.transportationProblemData.supply.contentEquals(suppliersAdapter.list.toIntArray()))
            suppliersAdapter.updateData(TransportationProblemSingleton.transportationProblemData.supply.toMutableList())
    }

    private fun initSuppliersRecycler() {
        suppliersAdapter = SuppliersAdapter(
            TransportationProblemSingleton.transportationProblemData.supply.toMutableList(),
            onClickListener = { quantity ->
                onSupplyItemClick(quantity)
            },
            onLongClickListener = { quantity ->
                onSupplyItemLongClick(quantity)
            },
            onDeleteClickListener = { position ->
                onSupplyItemDeleteClick(position)
            },
            onStartDragListener = this
        )
        val callback: ItemTouchHelper.Callback = ItemTouchHelperCallback(
            suppliersAdapter,
            isLongPressDragEnabled = true,
            isItemViewSwipeEnabled = true
        )
        touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(rv_suppliers)
        rv_suppliers.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_suppliers.adapter = suppliersAdapter
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        touchHelper.startDrag(viewHolder)
    }

    private fun onSupplyItemClick(quantity: Int) {}

    private fun onSupplyItemLongClick(quantity: Int) {}

    private fun onSupplyItemDeleteClick(position: Int) {
        suppliersAdapter.list.removeAt(position)
        suppliersAdapter.notifyDataSetChanged()
    }

    private fun setOnClickListeners() {
        floating_action_button.setOnClickListener {
            val dialog = EditTextWithTwoButtonsAndTextViewDialog.newInstance(
                title = getString(R.string.add_supplier),
                positiveBtnTitle = getString(R.string.add),
                negativeBtnTitle = getString(R.string.cancel),
                editTextHint = getString(R.string.enter_supply),
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
        suppliersAdapter.list.add(result)
        suppliersAdapter.notifyDataSetChanged()
    }
}
