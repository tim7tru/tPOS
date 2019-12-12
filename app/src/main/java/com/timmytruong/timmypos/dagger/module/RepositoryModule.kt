package com.timmytruong.timmypos.dagger.module

import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.mapper.SoupsExtrasMapper
import com.timmytruong.timmypos.repository.MenuRepository
import com.timmytruong.timmypos.repository.SoupsExtrasRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule
{
    @Singleton
    @Provides
    open fun providesSoupsExtraRepository(soupsExtrasMapper: SoupsExtrasMapper): SoupsExtrasRepository
    {
        return SoupsExtrasRepository(soupsExtrasMapper)
    }

    @Singleton
    @Provides
    open fun providesMenuRepository(menuMapper: MenuMapper): MenuRepository
    {
        return MenuRepository(menuMapper)
    }
}