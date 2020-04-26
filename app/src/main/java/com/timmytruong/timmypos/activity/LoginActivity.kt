package com.timmytruong.timmypos.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timmytruong.timmypos.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity: AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        log_in.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}

