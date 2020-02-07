package com.ainullov.kamil.transportation_problem.utils.adapter

import androidx.recyclerview.widget.RecyclerView

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean
    fun onItemDismiss(position: Int)
    fun onItemSelected(viewHolder: RecyclerView.ViewHolder?)
    fun onItemClear(viewHolder: RecyclerView.ViewHolder)
}