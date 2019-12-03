package com.timmytruong.timmypos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.models.MenuItem
import com.timmytruong.timmypos.repository.MenuRepository
import java.lang.Exception

class MenuViewModel: ViewModel()
{
    private var menu: MutableLiveData<List<MenuItem>>? = null
    private val repository: MenuRepository = MenuRepository()

    fun getMenu(): LiveData<List<MenuItem>>?
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
        repository.removeListener()
    }

    private fun loadMenu()
    {
        repository.addListener(callback)
    }

    private val callback: FirebaseDatabaseRepositoryCallback<MenuItem> =
        object: FirebaseDatabaseRepositoryCallback<MenuItem>
        {
            override fun onSuccess(result: List<MenuItem>)
            {
                menu!!.value = result
            }

            override fun onError(e: Exception)
            {
                menu!!.value = null
            }
        }
}