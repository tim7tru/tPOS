package com.timmytruong.timmypos.activity

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.fragments.FinancialFragment
import com.timmytruong.timmypos.fragments.HistoryFragment
import com.timmytruong.timmypos.fragments.OrdersFragment
import com.timmytruong.timmypos.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        CommonUtils.initializeCommonUtils(this)
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

        val ordersFragment = OrdersFragment()
        val historyFragment = HistoryFragment()
        val financialFragment = FinancialFragment()

        fragmentTransaction.add(content.id, ordersFragment, AppConstants.ORDERS_FRAGMENT_TAG)
            .add(content.id, historyFragment, AppConstants.HISTORY_FRAGMENT_TAG)
            .add(content.id, financialFragment, AppConstants.FINANCIAL_FRAGMENT_TAG)
            .show(ordersFragment)
            .hide(historyFragment)
            .hide(financialFragment)
            .commit()

        bottom_navigation_view.selectedItemId = R.id.navigation_orders
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean
    {
        val fragmentManager = supportFragmentManager
        val currentFragment: Fragment? = getVisibleFragment()
        var newFragment: Fragment? = null

        when (item.itemId)
        {
            R.id.navigation_orders ->
            {
                newFragment = fragmentManager.findFragmentByTag(AppConstants.ORDERS_FRAGMENT_TAG)
            }
            R.id.navigation_history ->
            {
                newFragment = fragmentManager.findFragmentByTag(AppConstants.HISTORY_FRAGMENT_TAG)
            }
            R.id.navigation_financials ->
            {
                newFragment = fragmentManager.findFragmentByTag(AppConstants.FINANCIAL_FRAGMENT_TAG)
            }
        }

        if (newFragment != currentFragment && currentFragment != null && newFragment != null)
        {
            fragmentManager.beginTransaction().hide(currentFragment).show(newFragment).commit()
        }

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

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus)
        {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}
