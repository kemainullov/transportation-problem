package com.ainullov.kamil.transportation_problem.presentation.ui.solution

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ainullov.kamil.transportation_problem.R

class SolutionFragment : Fragment() {

    companion object {
        fun newInstance() = SolutionFragment()
    }

    private lateinit var viewModel: SolutionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.solution_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SolutionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
