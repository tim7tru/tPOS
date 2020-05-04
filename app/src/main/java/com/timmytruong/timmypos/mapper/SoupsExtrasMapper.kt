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
            name = from[DataConstants.NODE_EXTRAS_NAME] as String,
            cost = from[DataConstants.NODE_EXTRAS_COST].toString(),
            optionTag = from[DataConstants.NODE_DIALOG_EXTRA_TAG] as String,
            category = from[DataConstants.NODE_EXTRAS_CATEGORY] as String
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
                    name = item.getString(DataConstants.NODE_EXTRAS_NAME),
                    cost = item.getLong(DataConstants.NODE_EXTRAS_COST).toString(),
                    optionTag = item.getString(DataConstants.NODE_DIALOG_EXTRA_TAG),
                    category = item.getString(DataConstants.NODE_EXTRAS_CATEGORY)
                ))
        }

        return listOfItems
    }
}