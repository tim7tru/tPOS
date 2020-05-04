package com.timmytruong.timmypos.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.timmytruong.timmypos.model.DialogOptionItem

@Dao
interface DialogOptionItemDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg extras: DialogOptionItem): List<Long>

    @Query("SELECT * FROM dialogoptionitem")
    suspend fun getAll(): List<DialogOptionItem>

    @Query("SELECT * FROM dialogoptionitem WHERE name = :name")
    suspend fun getOptionItem(name: String): DialogOptionItem

    @Query("DELETE FROM dialogoptionitem")
    suspend fun deleteAll()
}