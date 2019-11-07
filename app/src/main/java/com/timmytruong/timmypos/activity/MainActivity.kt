package com.timmytruong.timmypos.activity

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.timmytruong.timmypos.AppConstants
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.fragments.FinancialFragment
import com.timmytruong.timmypos.fragments.HistoryFragment
import com.timmytruong.timmypos.fragments.HomeFragment
import com.timmytruong.timmypos.fragments.OrdersFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setupNavigation()
        setupFragments()
    }

    private fun setupNavigation()
    {
        bottom_navigation_view.setOnNavigationItemSelectedListener(this)
    }

    private fun setupFragments()
    {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val homeFragment = HomeFragment()
        val ordersFragment = OrdersFragment()
        val historyFragment = HistoryFragment()
        val financialFragment = FinancialFragment()

        fragmentTransaction.add(content.id, homeFragment, AppConstants.HOME_FRAGMENT_TAG)
            .add(content.id, ordersFragment, AppConstants.ORDERS_FRAGMENT_TAG)
            .add(content.id, historyFragment, AppConstants.HISTORY_FRAGMENT_TAG)
            .add(content.id, financialFragment, AppConstants.FINANCIAL_FRAGMENT_TAG)
            .show(homeFragment)
            .hide(ordersFragment)
            .hide(historyFragment)
            .hide(financialFragment)

        bottom_navigation_view.selectedItemId = R.id.navigation_home

        fragmentTransaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val currentFragment: Fragment? = getVisibleFragment()
        var newFragment: Fragment? = null

        when (item.itemId)
        {
            R.id.navigation_home ->
            {
                Log.i("Fragment", "HOME")

                newFragment = fragmentManager.findFragmentByTag(AppConstants.HOME_FRAGMENT_TAG)
                if (newFragment == null)
                {
                    newFragment = HomeFragment()
                    fragmentTransaction.add(content.id, newFragment, AppConstants.HOME_FRAGMENT_TAG)
                }
            }
            R.id.navigation_orders ->
            {
                Log.i("Fragment", "ORDERS")

                newFragment = fragmentManager.findFragmentByTag(AppConstants.ORDERS_FRAGMENT_TAG)
                if (newFragment == null)
                {
                    newFragment = OrdersFragment()
                    fragmentTransaction.add(content.id, newFragment, AppConstants.ORDERS_FRAGMENT_TAG)

                }
            }
            R.id.navigation_history ->
            {
                Log.i("Fragment", "HISTORY")

                newFragment = fragmentManager.findFragmentByTag(AppConstants.HISTORY_FRAGMENT_TAG)
                if (newFragment == null)
                {
                    newFragment = HistoryFragment()
                    fragmentTransaction.add(content.id, newFragment, AppConstants.HISTORY_FRAGMENT_TAG)
                }
            }
            R.id.navigation_financials ->
            {
                Log.i("Fragment", "FINANCIALS")

                newFragment = fragmentManager.findFragmentByTag(AppConstants.FINANCIAL_FRAGMENT_TAG)
                if (newFragment == null)
                {
                    newFragment = FinancialFragment()
                    fragmentTransaction.add(content.id, newFragment, AppConstants.FINANCIAL_FRAGMENT_TAG)
                }
            }
        }

        if (currentFragment != null && currentFragment != newFragment && newFragment != null)
        {
            fragmentTransaction.hide(currentFragment).show(newFragment)
        }
        else if (currentFragment == null && newFragment != null)
        {
            fragmentTransaction.show(newFragment)
        }

        fragmentTransaction.commit()

        return true
    }

    private fun getVisibleFragment(): Fragment?
    {
        val fragmentManager = supportFragmentManager

        val fragments: List<Fragment?> = fragmentManager.fragments

        for (fragment in fragments)
        {
            if (fragment != null && fragment.isVisible)
            {
                return fragment
            }
        }

        return null
    }
}
