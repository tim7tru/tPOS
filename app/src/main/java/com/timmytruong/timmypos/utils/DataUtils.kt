package com.timmytruong.timmypos.utils

import android.content.Context
import android.content.res.AssetManager
import com.timmytruong.timmypos.models.DialogOptionItem
import com.timmytruong.timmypos.models.MenuItem
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

object DataUtils
{
    @Suppress("UNCHECKED_CAST")
    fun getMenuDataFromAssets(context: Context): ArrayList<ArrayList<MenuItem>>
    {
        val assets: AssetManager = context.assets

        val listOfLists: ArrayList<ArrayList<MenuItem>> = arrayListOf()

        for (nodeName in DataConstants.MENU_NODE_ARRAY)
            {
            val jsonObject = loadJSONFromAsset(assets, nodeName)

            if (jsonObject != null)
            {
                val rootNode = JSONObject(jsonObject)

                val menuNode = rootNode.getJSONObject(DataConstants.MENU_NODE)

                val categoryNode = menuNode.getJSONArray(DataConstants.APPETIZERS_NODE)

                listOfLists.add(menuJSONMapper(categoryNode))
            }
        }

        return listOfLists
    }

    fun getSoupsExtrasDataFromAssets(context: Context): ArrayList<DialogOptionItem>
    {
        val assets: AssetManager = context.assets

        val jsonObject = loadJSONFromAsset(assets, DataConstants.SOUPS_EXTRAS_NODE)

        if (jsonObject != null)
        {
            val rootNode = JSONObject(jsonObject)

            val soupsExtraNode = rootNode.getJSONObject(DataConstants.EXTRAS_NODE)

            val categoryNode: JSONArray = soupsExtraNode.getJSONArray(DataConstants.SOUPS_EXTRAS_NODE)

            return dialogOptionJSONMapper(categoryNode)
        }

        return arrayListOf()
    }

    private fun loadJSONFromAsset(assets: AssetManager, nodeName: String): String?
    {
        val json: String?

        try
        {
            val inputStream: InputStream = assets.open(nodeName + DataConstants.JSON_SUFFIX)

            val size: Int = inputStream.available()

            val buffer = ByteArray(size)

            inputStream.read(buffer)

            inputStream.close()

            json = String(buffer, Charsets.UTF_8)
    }
        catch (e: Exception)
        {
            e.printStackTrace()

            return null
        }

        return json
    }

    @Suppress("UNCHECKED_CAST")
    private fun menuJSONMapper(categoryNode: JSONArray): ArrayList<MenuItem>
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
                    tags = jsonArrayToArrayList(item.getJSONArray(DataConstants.TAGS_NODE)),
                    name = item.getString(DataConstants.NAME_NODE)
                )
            )
        }
        return listOfItems
    }

    private fun dialogOptionJSONMapper(categoryNode: JSONArray): ArrayList<DialogOptionItem>
    {
        val listOfItems: ArrayList<DialogOptionItem> = arrayListOf()

        for (index in 0 until categoryNode.length())
        {
            val item = categoryNode[index] as JSONObject

            listOfItems.add(
                DialogOptionItem(
                    name = item.getString(DataConstants.NAME_NODE),
                    cost = item.getLong(DataConstants.COST_NODE).toString(),
                    tag = item.getString(DataConstants.DIALOG_EXTRA_TAG_NODE)
                ))
        }

        return listOfItems
    }

    private fun jsonArrayToArrayList(jsonArray: JSONArray): ArrayList<String>
    {
        val arrayList: ArrayList<String> = arrayListOf()

        for (index in 0 until jsonArray.length())
        {
            arrayList.add(jsonArray.getString(index))
        }

        return arrayList
    }
}