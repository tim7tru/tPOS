package com.timmytruong.timmypos.repository

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import com.google.firebase.database.util.JsonMapper
import com.timmytruong.timmypos.models.MenuItem
import com.timmytruong.timmypos.utils.AppConstants

class MenuRepository(mapper: MenuMapper) : FirebaseDatabaseRepository<List<List<MenuItem>>>(mapper)
{
    override fun getRootNode(): String {
        return AppConstants.MENU_ROOT_NODE
    }

}