package com.timmytruong.timmypos.repository

import android.content.res.AssetManager
import com.timmytruong.timmypos.firebase.repository.FirebaseDatabaseRepository
import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.utils.DataUtils
import com.timmytruong.timmypos.utils.constants.DataConstants
import org.json.JSONArray
import org.json.JSONObject

class MenuRepository(private val menuMapper: MenuMapper): FirebaseDatabaseRepository<ArrayList<HashMap<Any, Any>>, ArrayList<MenuItem>>(menuMapper)
{
    override fun getRootNode(): String
    {
        return DataConstants.MENU_NODE
    }

    @Suppress("UNCHECKED_CAST")
    fun getMenuDataFromAssets(assets: AssetManager): ArrayList<ArrayList<MenuItem>>
    {
        val listOfLists: ArrayList<ArrayList<MenuItem>> = arrayListOf()

        val jsonObject = DataUtils.loadJSONFromAsset(assets, DataConstants.MENU_NODE)

        for (nodeName in DataConstants.MENU_NODE_ARRAY)
        {
            if (jsonObject != null)
            {
                val rootNode = JSONObject(jsonObject)

                val menuNode = rootNode.getJSONObject(DataConstants.MENU_NODE)

                if (menuNode.has(nodeName))
                {
                    val categoryNode = menuNode.getJSONArray(nodeName)

                    listOfLists.add(menuMapper.mapJSON(categoryNode))
                }
            }
        }

        return listOfLists
    }

}