package com.timmytruong.timmypos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.repository.MenuRepository
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants
import java.lang.Exception

class MenuViewModel: ViewModel()
{
    private var menu: MutableLiveData<List<ArrayList<MenuItem>>>? = null
    private val menuRepository: MenuRepository = MenuRepository()

    fun getMenu(): LiveData<List<ArrayList<MenuItem>>>?
    {
        if (menu == null)
        {
            menu = MutableLiveData()
            loadMenu()
        }
        return menu
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
                menu!!.value = result
            }

            override fun onError(e: Exception)
            {
                Log.d(AppConstants.FIREBASE_EXCEPTION_LOG_TAG, e.toString())

                menuRepository.postValue(DataConstants.ERRORS_NODE, CommonUtils.getCurrentDate(), e.stackTrace.toString())

                menu!!.value = null
            }
        }
}