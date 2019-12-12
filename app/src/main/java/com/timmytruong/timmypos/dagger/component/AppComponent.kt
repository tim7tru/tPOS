package com.timmytruong.timmypos.dagger.component

import com.timmytruong.timmypos.activity.LoginActivity
import com.timmytruong.timmypos.activity.MainActivity
import com.timmytruong.timmypos.activity.ViewOrderActivity
import com.timmytruong.timmypos.dagger.module.MapperModule
import com.timmytruong.timmypos.dagger.module.RepositoryModule
import com.timmytruong.timmypos.dagger.module.ViewModelFactoryModule
import com.timmytruong.timmypos.fragments.FinancialFragment
import com.timmytruong.timmypos.fragments.HistoryFragment
import com.timmytruong.timmypos.fragments.OrdersFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, MapperModule::class, ViewModelFactoryModule::class])
interface AppComponent
{
    fun inject(mainActivity: MainActivity)

    fun inject(loginActivity: LoginActivity)

    fun inject(viewOrderActivity: ViewOrderActivity)

    fun inject(ordersFragment: OrdersFragment)

    fun inject(historyFragment: HistoryFragment)

    fun inject(financialFragment: FinancialFragment)
}