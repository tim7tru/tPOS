package com.timmytruong.timmypos.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.model.CategoryMenuItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.repository.MenuRepository
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants
import java.lang.Exception
import javax.inject.Inject

class MenuViewModel @Inject constructor(private val menuRepository: MenuRepository): ViewModel()
{
    private var menu: MutableLiveData<List<ArrayList<MenuItem>>>? = null

    private var categoryItemsArray: ArrayList<MenuItem> = arrayListOf()

    private val categoryTitlesArray: ArrayList<CategoryMenuItem> = arrayListOf()

    private val menuItemsArray: ArrayList<ArrayList<MenuItem>> = arrayListOf()


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

    /**
     * INSERT FUNCTIONS
     */

    fun addCategoryTitle(item: CategoryMenuItem)
    {
        categoryTitlesArray.add(item)
    }

    fun addMenuItems(item: ArrayList<MenuItem>)
    {
        menuItemsArray.add(item)
    }

    /**
     *  GET FUNCTIONS
     */

    fun getCategoryItems(): ArrayList<MenuItem>
    {
        return categoryItemsArray
    }

    fun getCategoryTitles(): ArrayList<CategoryMenuItem>
    {
        return categoryTitlesArray
    }

    fun getMenuItems(): ArrayList<ArrayList<MenuItem>>
    {
        return menuItemsArray
    }

    /**
     * DELETE FUNCTIONS
     */

    fun clearMenuItems()
    {
        menuItemsArray.clear()
    }

    /**
     * SET FUNCTIONS
     */

    fun setCategoryItems(array: ArrayList<MenuItem>)
    {
        categoryItemsArray = array
    }

    fun setMenuItems(array: ArrayList<ArrayList<MenuItem>>)
    {
        menuItemsArray.addAll(array)
    }


}