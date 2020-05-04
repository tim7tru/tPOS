package com.timmytruong.timmypos.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.model.dao.MenuItemDao
import com.timmytruong.timmypos.utils.constants.RoomConstants
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.dao.DialogOptionItemDao
import com.timmytruong.timmypos.utils.TypeConverterUtils

@Database(entities = [MenuItem::class, DialogOptionItem::class], version = 1)
@TypeConverters(TypeConverterUtils::class)
abstract class MenuDatabase : RoomDatabase()
{
    abstract fun menuItemDao(): MenuItemDao

    abstract fun dialogOptionItemDao(): DialogOptionItemDao

    companion object
    {
        @Volatile
        private var instance: MenuDatabase? = null

        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                MenuDatabase::class.java,
                RoomConstants.DATABASE_NAME
        ).build()
    }
}