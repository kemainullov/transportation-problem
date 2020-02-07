package com.ainullov.kamil.transportation_problem.presentation.ui.consumers

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ainullov.kamil.transportation_problem.R

class ConsumersFragment : Fragment() {

    companion object {
        fun newInstance() = ConsumersFragment()
    }

    private lateinit var viewModel: ConsumersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.consumers_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ConsumersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
