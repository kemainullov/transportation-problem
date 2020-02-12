package com.ainullov.kamil.transportation_problem.utils.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND
import com.ainullov.kamil.transportation_problem.R
import kotlinx.android.synthetic.main.dialog_onclick_graph_item.*

class OnClickGraphItemDialog(context: Context, private val text: String) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_onclick_graph_item)
        window?.clearFlags(FLAG_DIM_BEHIND)
        tv_content.text = text
        setOnClickListeners()
    }


    private fun setOnClickListeners() {
        cv_dialog.setOnClickListener {
            dismiss()
        }
    }
}