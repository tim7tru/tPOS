package com.timmytruong.timmypos.dagger.module

import com.timmytruong.timmypos.mapper.SoupsExtrasMapper
import com.timmytruong.timmypos.repository.MenuRepository
import com.timmytruong.timmypos.repository.SoupsExtrasRepository
import com.timmytruong.timmypos.viewmodel.factory.MenuViewModelFactory
import com.timmytruong.timmypos.viewmodel.factory.SoupsExtrasViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelFactoryModule
{
    @Singleton
    @Provides
    open fun providesSoupsExtraViewModelFactory(soupsExtrasRepository: SoupsExtrasRepository): SoupsExtrasViewModelFactory
    {
        return SoupsExtrasViewModelFactory(soupsExtrasRepository)
    }

    @Singleton
    @Provides
    open fun providesMenuViewModelFactory(menuRepository: MenuRepository): MenuViewModelFactory
    {
        return MenuViewModelFactory(menuRepository)
    }
}