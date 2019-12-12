package com.timmytruong.timmypos.dagger.module

import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.mapper.SoupsExtrasMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModule
{
    @Singleton
    @Provides
    open fun providesSoupsExtrasMapper(): SoupsExtrasMapper
    {
        return SoupsExtrasMapper()
    }

    @Singleton
    @Provides
    open fun providesMenuMapper(): MenuMapper
    {
        return MenuMapper()
    }
}