package com.ainullov.kamil.transportation_problem.presentation.ui.consumers.adapter

import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.utils.adapter.OnStartDragListener
import kotlinx.android.synthetic.main.item_consumer.view.*

class ConsumerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(
        position: Int,
        quantity: Int,
        onClickListener: (Int) -> Unit,
        onLongClickListener: (Int) -> Unit,
        onDeleteClickListener: (Int) -> Unit,
        onStartDragListener: OnStartDragListener
    ) {
        itemView.tv_consumer.text = itemView.context.resources.getString(
            R.string.two_words_without_separating,
            itemView.context.resources.getString(R.string.consumer_b),
            (position + 1).toString()
        )
        itemView.tv_demand.text = itemView.context.resources.getString(
            R.string.two_words_separated_by_colon,
            itemView.context.resources.getString(R.string.demand),
            quantity.toString()
        )
        itemView.iv_delete.setOnClickListener { onDeleteClickListener(position) }
        itemView.iv_drag.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN)
                onStartDragListener.onStartDrag(this)
            return@setOnTouchListener true
        }

        itemView.setOnClickListener { onClickListener(quantity) }
        itemView.setOnLongClickListener {
            onLongClickListener(quantity)
            true
        }
    }
}