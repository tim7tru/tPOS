package com.timmytruong.timmypos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.timmytruong.timmypos.utils.constants.RoomConstants

@Entity
data class DialogOptionItem(
        @PrimaryKey
        @ColumnInfo(name = RoomConstants.DB_COL_EXTRAS_NAME)
        var name: String = "",

        @Ignore
        var checkedStatus: Boolean = false,

        @ColumnInfo(name =  RoomConstants.DB_COL_EXTRAS_COST)
        var cost: String = "",

        @ColumnInfo(name =  RoomConstants.DB_COL_EXTRAS_CATEGORY)
        var category: String = "",

        @ColumnInfo(name =  RoomConstants.DB_COL_EXTRAS_OPTIONS_TAG)
        var optionTag: String = "")
{
    fun resetChecked()
    {
        checkedStatus = false
    }
}