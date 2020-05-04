package com.timmytruong.timmypos.mapper

import com.timmytruong.timmypos.firebase.mapper.FirebaseMapper
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants

class MenuMapper : FirebaseMapper<HashMap<Any, Any>, MenuItem>()
{
    @Suppress("UNCHECKED_CAST")
    override fun map(from: HashMap<Any, Any>): MenuItem = MenuItem(
            menu_id = (from[DataConstants.NODE_MENU_ID] as Long).toInt(),
            category_id = (from[DataConstants.NODE_MENU_CATEGORY_ID] as Long).toInt(),
            category_name = from[DataConstants.NODE_MENU_CATEGORY_NAME] as String,
            availablity = from[DataConstants.NODE_MENU_AVAILABILITY] as Boolean,
            description = from[DataConstants.NODE_MENU_DESCRIPTION] as String,
            cost = AppConstants.DECIMAL_FORMAT.format(from[DataConstants.NODE_MENU_COST])
                    .toString(),
            dialog_type = from[DataConstants.NODE_MENU_DIALOG_TYPE] as String,
            tags = (from[DataConstants.NODE_MENU_TAGS] as List<String>?) ?: listOf(),
            name = from[DataConstants.NODE_MENU_NAME] as String
    )
}