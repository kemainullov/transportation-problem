package com.ainullov.kamil.transportation_problem.utils.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/*
Swipe, drag and drop for items in RecyclerView
*/
class ItemTouchHelperCallback(private val adapter: ItemTouchHelperAdapter,
                              private val isLongPressDragEnabled: Boolean,
                              private val isItemViewSwipeEnabled: Boolean) :
    ItemTouchHelper.Callback() {
    override fun isLongPressDragEnabled(): Boolean = isLongPressDragEnabled

    override fun isItemViewSwipeEnabled(): Boolean = isItemViewSwipeEnabled

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(
            dragFlags,
            swipeFlags
        )
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun onMove(
        recyclerView: RecyclerView, viewHolder: ViewHolder,
        target: ViewHolder
    ): Boolean {
        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSelectedChanged(viewHolder: ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            adapter.onItemSelected(viewHolder)
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (!recyclerView.isComputingLayout)
            adapter.onItemClear(viewHolder)
    }
}