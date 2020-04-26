package com.timmytruong.timmypos.activity

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.fragments.FinancialFragment
import com.timmytruong.timmypos.fragments.HistoryFragment
import com.timmytruong.timmypos.fragments.OrdersFragment
import com.timmytruong.timmypos.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    private lateinit var navController: NavController

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        CommonUtils.initializeCommonUtils(this)

        setupNavigation()
    }

    private fun setupNavigation()
    {
        navController = Navigation.findNavController(this, R.id.main_fragment)

        bottom_navigation_view.setupWithNavController(navController)
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
