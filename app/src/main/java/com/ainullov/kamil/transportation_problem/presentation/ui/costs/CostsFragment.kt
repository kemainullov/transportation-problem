package com.ainullov.kamil.transportation_problem.presentation.ui.costs

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.presentation.ui.costs.adapter.CostsAdapter
import kotlinx.android.synthetic.main.costs_fragment.*


class CostsFragment : Fragment() {

    companion object {
        fun newInstance() = CostsFragment()
    }

    private lateinit var viewModel: CostsViewModel
    private lateinit var costsAdapter: CostsAdapter
    val consumers = 17
    val suppliers = 20
    val initialList = List<Int>(consumers*suppliers) {0}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.costs_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CostsViewModel::class.java)

//        etDescriptionValue.movementMethod = ScrollingMovementMethod()
//        etDescriptionValue.setOnTouchListener { v, event ->
//            settingsSV.requestDisallowInterceptTouchEvent(true)
//            if (!v.canScrollVertically(1))
//                tvTransparent.visibility = View.GONE else tvTransparent.visibility = View.VISIBLE
//            false
//        }

        initCostsRecycler()
//        addGridLayoutWithEditTexts()
    }

    private fun initCostsRecycler() {
        costsAdapter = CostsAdapter(
            initialList.toMutableList(),
            onClickListener = { quantity ->
            },
            onLongClickListener = { quantity ->
            })

        rv_costs.layoutManager =
            GridLayoutManager(activity, consumers)
        rv_costs.adapter = costsAdapter
    }

//    private fun addGridLayoutWithEditTexts(){
//        val editTexts =
//            Array(10) { arrayOfNulls<EditText>(10) }
//
//        val gridLayout = GridLayout(activity)
//
//        //define how many rows and columns to be used in the layout
//        //define how many rows and columns to be used in the layout
//        gridLayout.setRowCount(10)
//        gridLayout.setColumnCount(10)
//
//        for (i in 0 until 10) {
//            for (j in 0 until 10) {
//                editTexts[i][j] = EditText(activity)
//                setPosition(editTexts[i][j], i, j)
//                gridLayout.addView(editTexts[i][j])
//            }
//        }
//        nested_sv_costs.addView(gridLayout)
//    }
//
//    //putting the edit text according to row and column index
//    private fun setPosition(editText: EditText?, row: Int, column: Int){
//        val param: GridLayout.LayoutParams = GridLayout.LayoutParams()
//        param.width = 400
//        param.height = 400
//        param.setGravity(Gravity.CENTER)
//        param.rowSpec = GridLayout.spec(row)
//        param.columnSpec = GridLayout.spec(column)
//        editText?.layoutParams = param
//    }

}
