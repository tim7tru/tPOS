package com.timmytruong.timmypos.repository

import com.timmytruong.timmypos.firebase.repository.FirebaseDatabaseRepository
import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.utils.constants.DataConstants

class MenuRepository(menuMapper: MenuMapper): FirebaseDatabaseRepository<ArrayList<HashMap<Any, Any>>, ArrayList<MenuItem>>(menuMapper)
{
    override fun getRootNode(): String
    {
        return DataConstants.MENU_NODE
    }
}