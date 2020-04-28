package com.timmytruong.timmypos.viewmodel

import android.content.res.AssetManager
import android.util.Log
import android.view.Menu
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.repository.MenuRepository
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants
import java.lang.Exception

class MenuViewModel: ViewModel()
{
    private var menu= MutableLiveData<List<ArrayList<MenuItem>>>()

    private val menuMapper: MenuMapper = MenuMapper()

    private val menuRepository: MenuRepository = MenuRepository(menuMapper = menuMapper)

    fun getMenu(): LiveData<List<ArrayList<MenuItem>>>
    {
        loadMenu()

        return menu
    }

    fun getMenu(assets: AssetManager)
    {
        menu.value = menuRepository.getMenuDataFromAssets(assets)
    }

    override fun onCleared()
    {
        menuRepository.removeListener()
    }

    private fun loadMenu()
    {
        menuRepository.addListener(callback)
    }

    private val callback: FirebaseDatabaseRepositoryCallback<ArrayList<MenuItem>> =
        object: FirebaseDatabaseRepositoryCallback<ArrayList<MenuItem>>
        {
            override fun onSuccess(result: List<ArrayList<MenuItem>>)
            {
                menu.value = result
            }

            override fun onError(e: Exception)
            {
                Log.d(AppConstants.FIREBASE_EXCEPTION_LOG_TAG, e.toString())

                menuRepository.postValue(DataConstants.ERRORS_NODE, CommonUtils.getCurrentDate(), e.stackTrace.toString())

                menu.value = null
            }
        }
}