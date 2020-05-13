package com.timmytruong.timmypos.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.databinding.ActivityIntroBinding
import com.timmytruong.timmypos.interfaces.IntroClickListener

class IntroActivity : AppCompatActivity(), IntroClickListener
{
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val dataBinding = DataBindingUtil.setContentView<ActivityIntroBinding>(this, R.layout.activity_intro)

        dataBinding.listener = this

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
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

    override fun onCustomerClicked(view: View)
    {
        view.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.button_click_anim))

        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)
    }

    override fun onStaffClicked(view: View)
    {
        view.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.button_click_anim))

    }
}
