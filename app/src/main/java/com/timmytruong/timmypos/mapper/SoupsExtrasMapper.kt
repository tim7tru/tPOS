package com.timmytruong.timmypos.mapper

import com.timmytruong.timmypos.firebase.mapper.FirebaseMapper
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.utils.constants.DataConstants
import org.json.JSONArray
import org.json.JSONObject

class SoupsExtrasMapper: FirebaseMapper<HashMap<Any,Any>, DialogOptionItem>()
{
    override fun map(from: HashMap<Any, Any>): DialogOptionItem
    {
        return DialogOptionItem(
            name = from[DataConstants.NAME_NODE] as String,
            cost = from[DataConstants.COST_NODE].toString(),
            optionTag = from[DataConstants.DIALOG_EXTRA_TAG_NODE] as String,
            category = from[DataConstants.CATEGORY_NODE] as String
        )
    }

    fun mapJSON(categoryNode: JSONArray): ArrayList<DialogOptionItem>
    {
        val listOfItems: ArrayList<DialogOptionItem> = arrayListOf()

        for (index in 0 until categoryNode.length())
        {
            val item = categoryNode[index] as JSONObject

            listOfItems.add(
                DialogOptionItem(
                    name = item.getString(DataConstants.NAME_NODE),
                    cost = item.getLong(DataConstants.COST_NODE).toString(),
                    optionTag = item.getString(DataConstants.DIALOG_EXTRA_TAG_NODE),
                    category = item.getString(DataConstants.CATEGORY_NODE)
                ))
        }

        return listOfItems
    }
}