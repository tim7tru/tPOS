package com.timmytruong.timmypos.repository

import com.timmytruong.timmypos.firebase.repository.FirebaseDatabaseRepository
import com.timmytruong.timmypos.mapper.SoupsExtrasMapper
import com.timmytruong.timmypos.models.DialogOptionItem
import com.timmytruong.timmypos.utils.constants.DataConstants

class SoupsExtrasRepository: FirebaseDatabaseRepository<HashMap<Any, Any>, DialogOptionItem>(SoupsExtrasMapper())
{
    override fun getRootNode(): String
    {
        return DataConstants.EXTRAS_NODE + DataConstants.FORWARD + DataConstants.SOUPS_EXTRAS_NODE
    }
}