package com.timmytruong.timmypos.mapper

import com.timmytruong.timmypos.firebase.mapper.FirebaseMapper
import com.timmytruong.timmypos.models.MenuItem
import com.timmytruong.timmypos.utils.AppConstants
import com.timmytruong.timmypos.utils.DataConstants

class MenuMapper: FirebaseMapper<HashMap<Any, Any>, MenuItem>()
{
    @Suppress("UNCHECKED_CAST")
    override fun map(from: HashMap<Any, Any>): MenuItem
    {
        return MenuItem(
            availablity = from[DataConstants.AVAILABILITY_NODE] as Boolean,
            cost = (AppConstants.DECIMAL_FORMAT.format(from[DataConstants.COST_NODE])).toString(),
            description = from[DataConstants.DESCRIPTION_NODE] as String,
            dialogType =  from[DataConstants.DIALOG_TYPE_NODE] as String,
            tags = from[DataConstants.TAGS_NODE] as ArrayList<String>?,
            name = from[DataConstants.NAME_NODE] as String
        )
    }
}