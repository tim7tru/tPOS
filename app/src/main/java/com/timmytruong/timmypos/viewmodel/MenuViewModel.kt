package com.timmytruong.timmypos.viewmodel

import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.model.CategoryMenuItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.provider.MenuProvider
import com.timmytruong.timmypos.repository.MenuRepository
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants

class MenuViewModel : ViewModel()
{
    private var menu = MutableLiveData<List<ArrayList<MenuItem>>>()

    private val menuProvider: MenuProvider = MenuProvider()

    private val menuMapper: MenuMapper = MenuMapper()

    private val menuRepository: MenuRepository = MenuRepository(menuMapper = menuMapper)

    private val callback: FirebaseDatabaseRepositoryCallback<ArrayList<MenuItem>> =
            object : FirebaseDatabaseRepositoryCallback<ArrayList<MenuItem>>
            {
                override fun onSuccess(result: List<ArrayList<MenuItem>>)
                {
                    menu.value = result
                }

                override fun onError(e: Exception)
                {
                    Log.d(AppConstants.FIREBASE_EXCEPTION_LOG_TAG, e.toString())

                    menuRepository.postValue(
                            DataConstants.ERRORS_NODE,
                            CommonUtils.getCurrentDate(),
                            e.stackTrace.toString()
                    )

                    menu.value = null
                }
            }

    fun setupData()
    {
        setCategoryTitles()
    }

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

    fun getCurrentCategoryTitle(): String
    {
        return menuProvider.getCurrenCategoryTitle()
    }

    fun getItemCount(): Int
    {
        return menuProvider.getItemCount()
    }

    fun getCategoryTitles(): ArrayList<CategoryMenuItem>
    {
        return menuProvider.getCategoryTitles()
    }

    fun getCategoryItems(): ArrayList<MenuItem>
    {
        return menuProvider.getCategoryItems()
    }

    private fun setCategoryTitles()
    {
        menuProvider.createCategoryData()
    }

    fun onMenuRetrieved(menu: List<ArrayList<MenuItem>>)
    {
        menuProvider.onMenuRetrieved(menu = menu)
    }

    fun onCategoryMenuItemClicked(oldPosition: Int, newPosition: Int)
    {
        menuProvider.onCategoryMenuItemClicked(
                oldPosition = oldPosition,
                newPosition = newPosition
        )
    }

    fun addToOrder(orderedItem: OrderedItem)
    {
        menuProvider.addToOrder(orderedItem)
    }

}