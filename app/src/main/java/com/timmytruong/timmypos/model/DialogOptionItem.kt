package com.timmytruong.timmypos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timmytruong.timmypos.utils.constants.RoomConstants

@Entity
data class DialogOptionItem(
        @PrimaryKey
        @ColumnInfo(name = RoomConstants.DB_COL_EXTRAS_NAME)
        var name: String = "",

        @ColumnInfo(name = RoomConstants.DB_COL_EXTRAS_CHECKED_STATUS)
        var checkedStatus: Boolean = false,

        @ColumnInfo(name =  RoomConstants.DB_COL_EXTRAS_COST)
        var cost: String = "",

        @ColumnInfo(name =  RoomConstants.DB_COL_EXTRAS_CATEGORY)
        var categoryName: String = "",

        @ColumnInfo(name = RoomConstants.DB_COL_EXTRAS_CATEGORY_ID)
        var categoryId: Int = -1,

        @ColumnInfo(name =  RoomConstants.DB_COL_EXTRAS_OPTION_TAG)
        var optionTag: String = ""
)
{
    fun resetChecked()
    {
        checkedStatus = false
    }
}