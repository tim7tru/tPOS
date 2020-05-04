package com.timmytruong.timmypos.model.dao

import androidx.room.Dao
import com.timmytruong.timmypos.model.MenuItem
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MenuItemDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg menu: MenuItem): List<Long>

    @Query("SELECT * FROM menuitem")
    suspend fun getAll(): List<MenuItem>

    @Query("SELECT * FROM menuitem WHERE menu_id = :menuNumber")
    suspend fun getMenuItem(menuNumber: Int): MenuItem

    @Query("DELETE FROM menuitem")
    suspend fun deleteAll()
}