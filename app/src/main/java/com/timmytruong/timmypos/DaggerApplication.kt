package com.timmytruong.timmypos

import android.app.Application
import com.timmytruong.timmypos.dagger.component.AppComponent
import com.timmytruong.timmypos.dagger.component.DaggerAppComponent


class DaggerApplication: Application()
{
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        createAppComponent()
    }

    private fun createAppComponent()
    {
        appComponent = DaggerAppComponent.builder().build()
    }
}