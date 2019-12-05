package com.timmytruong.timmypos.mapper

import com.timmytruong.timmypos.firebase.mapper.FirebaseMapper
import com.timmytruong.timmypos.models.MenuItem
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants

class MenuMapper: FirebaseMapper<ArrayList<HashMap<Any, Any>>, ArrayList<MenuItem>>()
{
    @Suppress("UNCHECKED_CAST")
    override fun map(from: ArrayList<HashMap<Any, Any>>): ArrayList<MenuItem>
    {
        val listOfItems: ArrayList<MenuItem> = arrayListOf()
        if (from.isNotEmpty())
        {
            from.forEach { element: HashMap<Any, Any> ->
                listOfItems.add(
                    MenuItem(
                        menuNumber = (element[DataConstants.MENU_NUMBER_NODE] as Long).toInt(),
                        availablity = element[DataConstants.AVAILABILITY_NODE] as Boolean,
                        description = element[DataConstants.DESCRIPTION_NODE] as String,
                        cost = AppConstants.DECIMAL_FORMAT.format(element[DataConstants.COST_NODE]).toString(),
                        dialogType = element[DataConstants.DIALOG_TYPE_NODE] as String,
                        tags = element[DataConstants.TAGS_NODE] as ArrayList<String>?,
                        name = element[DataConstants.NAME_NODE] as String
                    )
                )
            }
        }
        return listOfItems
    }
}