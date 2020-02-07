package com.ainullov.kamil.transportation_problem.presentation.ui.costs

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ainullov.kamil.transportation_problem.R

class CostsFragment : Fragment() {

    companion object {
        fun newInstance() = CostsFragment()
    }

    private lateinit var viewModel: CostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.costs_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CostsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
