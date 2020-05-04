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
        return DataConstants.NODE_EXTRAS + DataConstants.FORWARD + DataConstants.NODE_EXTRAS_SOUPS_EXTRAS
    }

    fun getSoupsExtrasDataFromAssets(assets: AssetManager): ArrayList<DialogOptionItem>
    {
        val jsonObject = DataUtils.loadJSONFromAsset(assets, DataConstants.NODE_EXTRAS_SOUPS_EXTRAS)

        if (jsonObject != null)
        {
            val rootNode = JSONObject(jsonObject)

            val soupsExtraNode = rootNode.getJSONObject(DataConstants.NODE_EXTRAS)

            val categoryNode: JSONArray = soupsExtraNode.getJSONArray(DataConstants.NODE_EXTRAS_SOUPS_EXTRAS)

            return soupsExtrasMapper.mapJSON(categoryNode)
        }

        return arrayListOf()
    }

}