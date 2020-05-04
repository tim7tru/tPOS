package com.timmytruong.timmypos.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.model.CategoryMenuItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.model.database.MenuDatabase
import com.timmytruong.timmypos.provider.MenuProvider
import com.timmytruong.timmypos.repository.MenuRepository
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.PreferenceUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : BaseViewModel(application)
{
    val menu = MutableLiveData<List<MenuItem>>()

    val orderItems = MutableLiveData<ArrayList<MenuItem>>()

    val loadingMenu = MutableLiveData<Boolean>()

    private var refreshTime = DataConstants.DEFAULT_REFRESH_TIME

    private val menuProvider: MenuProvider = MenuProvider()

    private val menuMapper: MenuMapper = MenuMapper()

    private val prefUtils = PreferenceUtils(getApplication())

    private val menuRepository: MenuRepository = MenuRepository(menuMapper = menuMapper)

    private val callback: FirebaseDatabaseRepositoryCallback<MenuItem> =
            object : FirebaseDatabaseRepositoryCallback<MenuItem>
            {
                override fun onError(e: Exception)
                {
                    Log.d(AppConstants.FIREBASE_EXCEPTION_LOG_TAG, e.toString())

                    menuRepository.postValue(
                            DataConstants.NODE_ERRORS,
                            CommonUtils.getCurrentDate(),
                            e.stackTrace.toString()
                    )

                    menu.value = null
                }

                override fun onSuccess(result: List<MenuItem>)
                {
                    Toast.makeText(getApplication(), "Data loaded from Firebase", Toast.LENGTH_SHORT).show()

                    storeMenuLocally(result)

                    menuRepository.removeListener()
                }
            }

    override fun onCleared()
    {
        menuRepository.removeListener()
    }

    private fun storeMenuLocally(list: List<MenuItem>)
    {
        launch {
            val dao = MenuDatabase(getApplication()).menuItemDao()

            dao.deleteAll()

            for (category in list.indices)
            {
                dao.insertAll(*list.toTypedArray())
            }

            menuRetrievedFromFirebase(list)
        }

        prefUtils.saveUpdateTime(System.nanoTime())
    }

    private fun menuRetrievedFromFirebase(list: List<MenuItem>)
    {
        menu.value = list
    }

    fun fetch()
    {
        checkCacheDuration()

        val updateTime = prefUtils.getUpdateTime()

        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime)
        {
            loadMenuFromDatabase()
            Toast.makeText(getApplication(), "Data loaded from Room", Toast.LENGTH_SHORT).show()
        }
        else
        {
            loadMenuFromFirebase()
        }
    }

    private fun checkCacheDuration()
    {
        val cachePreference = prefUtils.getCacheDuration()

        try
        {
            val cachePreferenceInt = cachePreference?.toInt() ?: 10

            refreshTime = cachePreferenceInt.times(1000 * 1000 * 1000L)
        }
        catch (e: NumberFormatException)
        {
            e.printStackTrace()
        }
    }

    private fun loadMenuFromFirebase()
    {
        menuRepository.addListener(callback)
    }

    private fun loadMenuFromDatabase()
    {
        launch {
            val menu = MenuDatabase(getApplication()).menuItemDao().getAll()

            menuRetrievedFromFirebase(menu)
        }
    }

    fun setupData()
    {
        menuProvider.createCategoryData()
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

    fun getCategoryItems(activeCategory: String): ArrayList<MenuItem>
    {
        return menuProvider.getCategoryItems(activeCategory)
    }

    fun onMenuRetrieved(menu: List<MenuItem>)
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

    fun refreshBypassCache()
    {
        loadMenuFromFirebase()
    }
}