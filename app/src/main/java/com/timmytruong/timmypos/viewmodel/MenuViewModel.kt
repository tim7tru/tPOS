package com.timmytruong.timmypos.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.model.CategoryMenuItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.Order
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

    val orderedItems = MutableLiveData<ArrayList<MenuItem>>()

    val loadingMenu = MutableLiveData<Boolean>()

    private var refreshTime = DataConstants.DEFAULT_REFRESH_TIME

    private val menuProvider: MenuProvider = MenuProvider()

    private val menuMapper: MenuMapper = MenuMapper()

    private val prefUtils = PreferenceUtils(context = getApplication())

    private val menuRepository: MenuRepository = MenuRepository(menuMapper = menuMapper)

    private val callback: FirebaseDatabaseRepositoryCallback<MenuItem> =
            object : FirebaseDatabaseRepositoryCallback<MenuItem>
            {
                override fun onError(e: Exception)
                {
                    Log.d(AppConstants.FIREBASE_EXCEPTION_LOG_TAG, e.toString())

                    menuRepository.postValue(
                            child = DataConstants.NODE_ERRORS,
                            key = CommonUtils.getCurrentDate(),
                            value = e.stackTrace.toString()
                    )

                    menu.value = null
                }

                override fun onSuccess(result: List<MenuItem>)
                {
                    Toast.makeText(
                            getApplication(),
                            "Data loaded from Firebase",
                            Toast.LENGTH_SHORT
                    ).show()

                    storeMenuLocally(list = result)

                    menuRepository.removeListener()
                }
            }

    private fun storeMenuLocally(list: List<MenuItem>)
    {
        launch {
            val dao = MenuDatabase(getApplication()).menuItemDao()

            dao.deleteAll()

            for (category in list.indices)
            {
                dao.insertAll(menu = *list.toTypedArray())
            }

            menuRetrievedFromFirebase(list = list)
        }

        prefUtils.saveUpdateTime(time = System.nanoTime())
    }

    private fun menuRetrievedFromFirebase(list: List<MenuItem>)
    {
        menu.value = list
    }

    fun fetch()
    {
        checkCacheDuration()

        val updateTime = prefUtils.getUpdateTime()

        if (updateTime != null && updateTime != 0L && System.currentTimeMillis() - updateTime < refreshTime)
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
        refreshTime = prefUtils.checkCacheDuration()
    }

    private fun loadMenuFromFirebase()
    {
        menuRepository.addListener(firebaseCallback = callback)
    }

    private fun loadMenuFromDatabase()
    {
        launch {
            val menu = MenuDatabase(getApplication()).menuItemDao().getAll()

            menuRetrievedFromFirebase(list = menu)
        }
    }

    fun setupData()
    {
        menuProvider.setCurrentCategoryTitle(menuProvider.getCategoryTitles()[0].name)
    }

    fun getCurrentCategoryTitle(): String
    {
        return menuProvider.getCurrenCategoryTitle()
    }

    fun getCategoryTitles(): ArrayList<CategoryMenuItem>
    {
        return menuProvider.getCategoryTitles()
    }

    fun getCategoryItems(activeCategory: String): ArrayList<MenuItem>
    {
        return menuProvider.getCategoryItems(activeCategory = activeCategory)
    }

    fun onMenuRetrieved(menu: List<MenuItem>)
    {
        menuProvider.onMenuRetrieved(menu = menu)
    }

    fun onCategoryMenuItemClicked(newPosition: Int)
    {
        menuProvider.onCategoryMenuItemClicked(newPosition = newPosition)
    }

    fun addToOrder(orderedItem: OrderedItem)
    {
        menuProvider.addToOrder(orderedItem = orderedItem)
    }

    fun getCurrentOrder(): Order
    {
        return menuProvider.getOrder()
    }

    fun refreshBypassCache()
    {
        loadMenuFromFirebase()
    }
}