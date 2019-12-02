package com.timmytruong.timmypos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timmytruong.timmypos.models.MenuItem
import com.timmytruong.timmypos.repository.MenuRepository

class MenuViewModel: ViewModel()
{
    private val menu: MutableLiveData<List<List<MenuItem>>>? = null

    fun getMenu(): LiveData<List<List<MenuItem>>>?
    {
        if (menu == null)
        {
            menu = MutableLiveData()
            loadMenu()
        }
        return menu
    }

    private fun loadMenu()
    {

    }
}