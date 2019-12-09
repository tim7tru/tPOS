package com.timmytruong.timmypos.mapper

import com.timmytruong.timmypos.firebase.mapper.FirebaseMapper
import com.timmytruong.timmypos.models.DialogOptionItem
import com.timmytruong.timmypos.utils.constants.DataConstants

class SoupsExtrasMapper: FirebaseMapper<HashMap<Any,Any>, DialogOptionItem>()
{
    override fun map(from: HashMap<Any, Any>): DialogOptionItem
    {
        return DialogOptionItem(
            name = from[DataConstants.NAME_NODE] as String,
            cost = from[DataConstants.COST_NODE].toString(),
            tag = from[DataConstants.DIALOG_EXTRA_TAG_NODE] as String
        )
    }
}