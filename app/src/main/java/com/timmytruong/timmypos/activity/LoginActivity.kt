package com.timmytruong.timmypos.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.databinding.ActivityLoginBinding
import com.timmytruong.timmypos.interfaces.LogInClickListener

class LoginActivity : AppCompatActivity(), LogInClickListener
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        val dataBinding: ActivityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        dataBinding.loginListener = this

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onLogInClicked(view: View)
    {
        val intent = Intent(this, IntroActivity::class.java)

        startActivity(intent)
    }
}

