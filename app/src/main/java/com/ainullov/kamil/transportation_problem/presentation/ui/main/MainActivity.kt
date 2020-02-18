package com.ainullov.kamil.transportation_problem.presentation.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.presentation.base.App
import com.ainullov.kamil.transportation_problem.presentation.ui.consumers.ConsumersFragment
import com.ainullov.kamil.transportation_problem.presentation.ui.costs.CostsFragment
import com.ainullov.kamil.transportation_problem.presentation.ui.history.HistoryActivity
import com.ainullov.kamil.transportation_problem.presentation.ui.main.pager_adapter.MainPagerAdapter
import com.ainullov.kamil.transportation_problem.presentation.ui.solution.SolutionFragment
import com.ainullov.kamil.transportation_problem.presentation.ui.suppliers.SuppliersFragment
import com.ainullov.kamil.transportation_problem.utils.Const
import com.ainullov.kamil.transportation_problem.utils.singletons.TransportationProblemSingleton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val SUPPLIERS_FRAGMENT = 0
        const val CONSUMERS_FRAGMENT = 1
        const val COSTS_FRAGMENT = 2
        const val SOLUTION_FRAGMENT = 3
        const val HISTORY_ACTIVITY = 4
        const val NULL = Int.MAX_VALUE
    }

    private lateinit var viewModel: MainActivityViewModel

    private val suppliersFragment = SuppliersFragment.newInstance()
    private val consumersFragment = ConsumersFragment.newInstance()
    private val costsFragment = CostsFragment.newInstance()
    private val solutionFragment = SolutionFragment.newInstance()
    private var currentFragment = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        initViewPager()
        initBottomBarListener()
        setOnClickListeners()
        lifecycle.addObserver(viewModel)
    }

    override fun onPause() {
        super.onPause()
        App.transportationProblemSharedPreferences.setTransportationProblemData(
            TransportationProblemSingleton.transportationProblemData
        )
        App.transportationProblemSharedPreferences.setCurrentSolutionId(
            TransportationProblemSingleton.problemSolutionId
        )
    }

    private fun setOnClickListeners() {
        ib_more.setOnClickListener { onMoreClicked() }
        ll_archive.setOnClickListener { onArchiveClicked() }
    }

    private fun onArchiveClicked() =
        startActivityForResult(HistoryActivity.newIntent(this), HISTORY_ACTIVITY)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == HISTORY_ACTIVITY) {
            main_content.setCurrentItem(SOLUTION_FRAGMENT, false)
            solutionFragment.onProblemSolutionChanged(
                data?.getLongExtra(
                    Const.Extras.SOLUTION_ID,
                    TransportationProblemSingleton.problemSolutionId
                ) ?: TransportationProblemSingleton.problemSolutionId
            )
        }
    }

    private fun onMoreClicked() {
        val popup = PopupMenu(this, ib_more)
        popup.gravity = Gravity.END
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.main_activity_more_menu, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.reset_task_conditions -> {
                    resetTaskConditions()
                    true
                }
                R.id.about -> {
                    startActivity(AboutAppActivity.newIntent(this))
                    true
                }
                R.id.exit -> {
                    finish() // TODO
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    private fun resetTaskConditions() {
        main_content.setCurrentItem(NULL, false)
        TransportationProblemSingleton.removeTransportationProblemSingletonData()
        main_content.setCurrentItem(SUPPLIERS_FRAGMENT, false)
    }

    private fun initBottomBarListener() {
        bottom_bar.onItemSelectedListener = { view, menuItem ->
            when (menuItem.itemId) {
                R.id.item_suppliers -> main_content.setCurrentItem(SUPPLIERS_FRAGMENT, false)
                R.id.item_consumers -> main_content.setCurrentItem(CONSUMERS_FRAGMENT, false)
                R.id.item_costs -> main_content.setCurrentItem(COSTS_FRAGMENT, false)
                R.id.item_solution -> main_content.setCurrentItem(SOLUTION_FRAGMENT, false)
            }
        }
    }

    private fun initViewPager() {
        val fragmentsList =
            listOf<Fragment>(suppliersFragment, consumersFragment, costsFragment, solutionFragment)
        main_content.offscreenPageLimit = 4
        main_content.adapter = MainPagerAdapter(supportFragmentManager, this, fragmentsList)
        val listener = object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) = selectPage(position)
        }
        main_content.addOnPageChangeListener(listener)
        setUpFirsFragment()
    }

    private fun setUpFirsFragment() {
        main_content.setCurrentItem(SUPPLIERS_FRAGMENT, false)
        name.text = getString(R.string.suppliers)
        name.setTextColor(resources.getColor(R.color.suppliers, null))
    }

    private fun selectPage(position: Int) {
        currentFragment = position
        when (position) {
            SUPPLIERS_FRAGMENT -> {
                name.text = getString(R.string.suppliers)
                name.setTextColor(resources.getColor(R.color.suppliers, null))
                bottom_bar.select(R.id.item_suppliers)
            }
            CONSUMERS_FRAGMENT -> {
                name.text = getString(R.string.consumers)
                name.setTextColor(resources.getColor(R.color.consumers, null))
                bottom_bar.select(R.id.item_consumers)
            }
            COSTS_FRAGMENT -> {
                name.text = getString(R.string.costs)
                name.setTextColor(resources.getColor(R.color.costs, null))
                bottom_bar.select(R.id.item_costs)
            }
            SOLUTION_FRAGMENT -> {
                name.text = getString(R.string.solution)
                name.setTextColor(resources.getColor(R.color.solution, null))
                bottom_bar.select(R.id.item_solution)
            }
            else -> {
                name.text = getString(R.string.suppliers)
                name.setTextColor(resources.getColor(R.color.suppliers, null))
                bottom_bar.select(R.id.item_suppliers)
            }
        }
    }
}
