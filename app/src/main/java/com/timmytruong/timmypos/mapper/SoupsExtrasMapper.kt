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
            optionTag = from[DataConstants.NODE_EXTRAS_OPTION_TAG] as String,
            categoryName = from[DataConstants.NODE_EXTRAS_CATEGORY] as String,
            categoryId= (from[DataConstants.NODE_EXTRAS_CATEGORY_ID] as Long).toInt(),
            checkedStatus = from[DataConstants.NODE_EXTRAS_CHECKED_STATUS] as Boolean
        )
    }
}