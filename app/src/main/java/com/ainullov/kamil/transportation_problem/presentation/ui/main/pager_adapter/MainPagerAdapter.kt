package com.ainullov.kamil.transportation_problem.presentation.ui.main.pager_adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class MainPagerAdapter(
    fm: FragmentManager,
    context: Context,
    newData: List<Fragment>
) :
    FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val ctx: Context = context
    private var data: List<Fragment> = ArrayList<Fragment>()
    override fun getItem(position: Int): Fragment {
        return data[position]
    }

    override fun getCount(): Int {
        return data.size
    }

    init {
        data = newData
    }
}
