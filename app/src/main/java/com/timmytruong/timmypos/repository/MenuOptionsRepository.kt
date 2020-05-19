package com.timmytruong.timmypos.repository

import com.timmytruong.timmypos.firebase.repository.FirebaseDatabaseRepository
import com.timmytruong.timmypos.mapper.MenuOptionsMapper
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.utils.constants.DataConstants

class MenuOptionsRepository(
        menuOptionsMapper: MenuOptionsMapper
) : FirebaseDatabaseRepository<HashMap<Any, Any>, DialogOptionItem>(menuOptionsMapper)
{
    override fun getRootNode(): String
    {
        return DataConstants.NODE_OPTIONS
    }
}