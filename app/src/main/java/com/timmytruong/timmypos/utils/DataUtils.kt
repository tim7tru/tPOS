package com.timmytruong.timmypos.utils

import android.content.Context
import android.content.res.AssetManager
import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.mapper.SoupsExtrasMapper
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.utils.constants.DataConstants
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream

object DataUtils
{
    @Suppress("UNCHECKED_CAST")
    fun getMenuDataFromAssets(context: Context): ArrayList<ArrayList<MenuItem>>
    {
        val mapper = MenuMapper()

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

                listOfLists.add(mapper.mapJSON(categoryNode))
            }
        }

        return arrayListOf()
    }

    fun getSoupsExtrasDataFromAssets(context: Context): ArrayList<DialogOptionItem>
    {
        val mapper = SoupsExtrasMapper()

        val assets: AssetManager = context.assets

        val jsonObject = loadJSONFromAsset(assets, DataConstants.SOUPS_EXTRAS_NODE)

        if (jsonObject != null)
        {
            val rootNode = JSONObject(jsonObject)

            val soupsExtraNode = rootNode.getJSONObject(DataConstants.EXTRAS_NODE)

            val categoryNode: JSONArray = soupsExtraNode.getJSONArray(DataConstants.SOUPS_EXTRAS_NODE)

            return mapper.mapJSON(categoryNode)
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

    fun jsonArrayToArrayList(jsonArray: JSONArray): ArrayList<String>
    {
        val arrayList: ArrayList<String> = arrayListOf()

        for (index in 0 until jsonArray.length())
        {
            arrayList.add(jsonArray.getString(index))
        }

        return arrayList
    }

    fun buildOrderedItem(item: MenuItem,
                         sizes: java.util.ArrayList<DialogOptionItem>? = null,
                         extras: java.util.ArrayList<DialogOptionItem>? = null,
                         broths: java.util.ArrayList<DialogOptionItem>? = null,
                         quantity: Int,
                         unitCost: Double): OrderedItem
    {
        val orderedItem = OrderedItem (
            name = item.name,
            menuNumber = item.menuNumber,
            quantity = quantity,
            unitCost = unitCost
        )

        if (!broths.isNullOrEmpty())
        {
            for (broth in broths)
            {
                if (broth.checkedStatus)
                {
                    orderedItem.broth = broth.name
                    break
                }
            }
        }

        if (!sizes.isNullOrEmpty())
        {
            for (size in sizes)
            {
                if (size.checkedStatus)
                {
                    orderedItem.size = size.name
                    break
                }
            }
        }

        if (!extras.isNullOrEmpty())
        {
            val selectedExtras: java.util.ArrayList<String> = arrayListOf()

            for (extra in extras)
            {
                if (extra.checkedStatus)
                {
                    selectedExtras.add(extra.name)
                }
            }

            orderedItem.extras = selectedExtras
        }

        return orderedItem
    }
}