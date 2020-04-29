package com.timmytruong.timmypos.repository

import android.content.res.AssetManager
import com.timmytruong.timmypos.firebase.repository.FirebaseDatabaseRepository
import com.timmytruong.timmypos.mapper.SoupsExtrasMapper
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.utils.DataUtils
import com.timmytruong.timmypos.utils.constants.DataConstants
import org.json.JSONArray
import org.json.JSONObject

class SoupsExtrasRepository(
    private val soupsExtrasMapper: SoupsExtrasMapper
): FirebaseDatabaseRepository<HashMap<Any, Any>, DialogOptionItem>(soupsExtrasMapper)
{
    override fun getRootNode(): String
    {
        return DataConstants.EXTRAS_NODE + DataConstants.FORWARD + DataConstants.SOUPS_EXTRAS_NODE
    }

    fun getSoupsExtrasDataFromAssets(assets: AssetManager): ArrayList<DialogOptionItem>
    {
        val jsonObject = DataUtils.loadJSONFromAsset(assets, DataConstants.SOUPS_EXTRAS_NODE)

        if (jsonObject != null)
        {
            val rootNode = JSONObject(jsonObject)

            val soupsExtraNode = rootNode.getJSONObject(DataConstants.EXTRAS_NODE)

            val categoryNode: JSONArray = soupsExtraNode.getJSONArray(DataConstants.SOUPS_EXTRAS_NODE)

            return soupsExtrasMapper.mapJSON(categoryNode)
        }

        return arrayListOf()
    }

}