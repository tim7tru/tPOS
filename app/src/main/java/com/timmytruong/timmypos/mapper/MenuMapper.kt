package com.timmytruong.timmypos.mapper

import com.timmytruong.timmypos.firebase.mapper.FirebaseMapper
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.utils.DataUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class MenuMapper @Inject constructor(): FirebaseMapper<ArrayList<HashMap<Any, Any>>, ArrayList<MenuItem>>()
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

    fun mapJSON(categoryNode: JSONArray): ArrayList<MenuItem>
    {
        val listOfItems: ArrayList<MenuItem> = arrayListOf()

        for (index in 0 until categoryNode.length())
        {
            val item = categoryNode[index] as JSONObject

            listOfItems.add(
                MenuItem(
                    menuNumber = (item.getLong(DataConstants.MENU_NUMBER_NODE)).toInt(),
                    availablity = item.getBoolean(DataConstants.AVAILABILITY_NODE),
                    description = item.getString(DataConstants.DESCRIPTION_NODE),
                    cost = AppConstants.DECIMAL_FORMAT.format(item.getLong(DataConstants.COST_NODE)).toString(),
                    dialogType = item.getString(DataConstants.DIALOG_TYPE_NODE),
                    tags = DataUtils.jsonArrayToArrayList(item.getJSONArray(DataConstants.TAGS_NODE)),
                    name = item.getString(DataConstants.NAME_NODE)
                )
            )
        }
        return listOfItems
    }
}