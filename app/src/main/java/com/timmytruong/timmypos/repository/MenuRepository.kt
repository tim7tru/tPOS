package com.timmytruong.timmypos.repository

import com.timmytruong.timmypos.firebase.repository.FirebaseDatabaseRepository
import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.models.MenuItem
import com.timmytruong.timmypos.utils.constants.DataConstants

class MenuRepository: FirebaseDatabaseRepository<ArrayList<HashMap<Any, Any>>, ArrayList<MenuItem>>(MenuMapper())
{
    override fun getRootNode(): String
    {
        return DataConstants.MENU_NODE
    }
}