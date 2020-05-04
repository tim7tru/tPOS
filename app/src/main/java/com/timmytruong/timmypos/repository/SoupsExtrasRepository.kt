package com.timmytruong.timmypos.repository

import android.content.res.AssetManager
import com.timmytruong.timmypos.firebase.repository.FirebaseDatabaseRepository
import com.timmytruong.timmypos.mapper.SoupsExtrasMapper
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.utils.DataUtils
import com.timmytruong.timmypos.utils.constants.DataConstants
import org.json.JSONArray
import org.json.JSONObject

class SoupsExtrasRepository(soupsExtrasMapper: SoupsExtrasMapper
): FirebaseDatabaseRepository<HashMap<Any, Any>, DialogOptionItem>(soupsExtrasMapper)
{
    override fun getRootNode(): String
    {
        return DataConstants.NODE_EXTRAS
    }
}