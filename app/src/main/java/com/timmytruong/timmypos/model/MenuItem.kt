package com.timmytruong.timmypos.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timmytruong.timmypos.utils.constants.RoomConstants

@Entity
data class MenuItem(
        @PrimaryKey
        @ColumnInfo(name = RoomConstants.DB_COL_MENU_ID)
        var menu_id: Int = -1,

        @ColumnInfo(name = RoomConstants.DB_COL_CATEGORY_ID)
        var category_id: Int = -1,

        @ColumnInfo(name = RoomConstants.DB_COL_CATEGORY_NAME)
        var category_name: String = "",

        @ColumnInfo(name = RoomConstants.DB_COL_AVAILABILITY)
        var availablity: Boolean = false,

        @ColumnInfo(name = RoomConstants.DB_COL_COST)
        var cost: String = "",

        @ColumnInfo(name = RoomConstants.DB_COL_DESCRIPTION)
        var description: String = "",

        @ColumnInfo(name = RoomConstants.DB_COL_DIALOG_TYPE)
        var dialog_type: String = "",

        @ColumnInfo(name = RoomConstants.DB_COL_MENU_ITEM_NAME)
        var name: String = "",

        @ColumnInfo(name = RoomConstants.DB_COL_TAGS)
        var tags: List<String> = listOf()
)
