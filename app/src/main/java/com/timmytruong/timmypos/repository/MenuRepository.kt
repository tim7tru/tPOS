package com.timmytruong.timmypos.repository

import com.timmytruong.timmypos.firebase.repository.FirebaseDatabaseRepository
import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.models.MenuItem
import com.timmytruong.timmypos.utils.DataConstants

class MenuRepository: FirebaseDatabaseRepository<HashMap<Any, Any>, MenuItem>(MenuMapper())
{
    override fun getRootNode(): String
    {
        return DataConstants.MENU_NODE + DataConstants.FORWARD + DataConstants.APPETIZERS_NODE
    }
}