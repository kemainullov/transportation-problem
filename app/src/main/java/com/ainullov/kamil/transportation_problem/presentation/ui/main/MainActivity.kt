package com.ainullov.kamil.transportation_problem.presentation.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.presentation.base.App
import com.ainullov.kamil.transportation_problem.presentation.ui.consumers.ConsumersFragment
import com.ainullov.kamil.transportation_problem.presentation.ui.costs.CostsFragment
import com.ainullov.kamil.transportation_problem.presentation.ui.main.pager_adapter.MainPagerAdapter
import com.ainullov.kamil.transportation_problem.presentation.ui.solution.SolutionFragment
import com.ainullov.kamil.transportation_problem.presentation.ui.suppliers.SuppliersFragment
import com.ainullov.kamil.transportation_problem.utils.singletons.TransportationProblemSingleton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val SUPPLIERS_FRAGMENT = 0
        const val CONSUMERS_FRAGMENT = 1
        const val COSTS_FRAGMENT = 2
        const val SOLUTION_FRAGMENT = 3
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
    }

    override fun onPause() {
        super.onPause()
        App.transportationProblemSharedPreferences.setTransportationProblemData(TransportationProblemSingleton.transportationProblemData)
    }

    private fun setOnClickListeners() {
        ib_more.setOnClickListener {
            onMoreClicked()
        }
        ll_archive.setOnClickListener {
            onArchiveClicked()
        }
    }

    private fun onArchiveClicked() {
    }

    private fun onMoreClicked() {
        val popup = PopupMenu(this, ib_more)
        popup.gravity = Gravity.END
        val inflater = popup.menuInflater
        inflater.inflate(R.menu.main_activity_more_menu, popup.menu)
        popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.about -> {
//                        startActivity(AboutAppActivity.newIntent(applicationContext)) //this@MainActivity
                        return true
                    }
                    R.id.exit -> {
                        finish() // TODO
                        return true
                    }
                    else -> return false
                }
            }
        })
        popup.show()
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

        bottom_bar.onItemReselectedListener = { view, menuItem ->
            /**
             * handle here all the click in already selected items
             */
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
