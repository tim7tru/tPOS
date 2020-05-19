package com.timmytruong.timmypos.viewmodel

import android.app.Application
import android.app.Dialog
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.mapper.MenuOptionsMapper
import com.timmytruong.timmypos.model.*
import com.timmytruong.timmypos.model.database.MenuDatabase
import com.timmytruong.timmypos.provider.MenuProvider
import com.timmytruong.timmypos.provider.MenuOptionsProvider
import com.timmytruong.timmypos.repository.MenuRepository
import com.timmytruong.timmypos.repository.MenuOptionsRepository
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.PreferenceUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : BaseViewModel(application)
{
    val menu = MutableLiveData<List<MenuItem>>()

    val menuOptions = MutableLiveData<List<DialogOptionItem>>()

    val orderedItems = MutableLiveData<Order>()

    val loadingMenu = MutableLiveData<Boolean>()

    private var refreshTime = DataConstants.DEFAULT_REFRESH_TIME

    private val menuMapper: MenuMapper = MenuMapper()

    private val menuProvider: MenuProvider = MenuProvider()

    private val menuRepository: MenuRepository = MenuRepository(menuMapper = menuMapper)

    private val prefUtils = PreferenceUtils(context = getApplication())

    private val menuOptionsMapper = MenuOptionsMapper()

    private val menuOptionsProvider = MenuOptionsProvider()

    private val menuOptionsRepository =
            MenuOptionsRepository(menuOptionsMapper = menuOptionsMapper)

    private val menuCallback: FirebaseDatabaseRepositoryCallback<MenuItem> =
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
                    storeMenuLocally(list = result)

                    menuRepository.removeListener()
                }
            }

    private val menuOptionsCallback: FirebaseDatabaseRepositoryCallback<DialogOptionItem> =
            object : FirebaseDatabaseRepositoryCallback<DialogOptionItem>
            {
                override fun onSuccess(result: List<DialogOptionItem>)
                {
                    storeOptionsLocally(list = result)
                }

                override fun onError(e: Exception)
                {
                    menuOptionsRepository.postValue(
                            child = DataConstants.NODE_ERRORS,
                            key = CommonUtils.getCurrentDate(),
                            value = e.stackTrace.toString()
                    )

                    menuOptions.value = null
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

    private fun storeOptionsLocally(list: List<DialogOptionItem>)
    {
        launch {
            val dao = MenuDatabase(getApplication()).dialogOptionItemDao()

            dao.deleteAll()

            for (category in list.indices)
            {
                dao.insertAll(extras = *list.toTypedArray())
            }

            menuOptionsRetrievedFromFirebase(list = list)
        }

        prefUtils.saveUpdateTime(time = System.nanoTime())
    }

    private fun menuOptionsRetrievedFromFirebase(list: List<DialogOptionItem>)
    {
        menuOptions.value = list
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

            loadOptionsFromDatabase()

            Toast.makeText(getApplication(), "Data loaded from Room", Toast.LENGTH_SHORT).show()
        }
        else
        {
            loadMenuFromFirebase()

            loadOptionsFromFirebase()
        }
    }

    private fun checkCacheDuration()
    {
        refreshTime = prefUtils.checkCacheDuration()
    }

    private fun loadOptionsFromFirebase()
    {
        menuOptionsRepository.addListener(firebaseCallback = menuOptionsCallback)
    }

    private fun loadOptionsFromDatabase()
    {
        launch {
            val extras = MenuDatabase(getApplication()).dialogOptionItemDao().getAll()

            menuOptionsRetrievedFromFirebase(list = extras)
        }
    }

    private fun loadMenuFromFirebase()
    {
        menuRepository.addListener(firebaseCallback = menuCallback)
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

    fun getCategoryTitles(): ArrayList<MenuCategory>
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

    fun onMenuOptionsRetrieved(options: List<DialogOptionItem>)
    {
        menuOptionsProvider.onSoupsExtrasRetrieved(newOptions = options)
    }

    fun getMenuOptions(categoryId: Int, optionType: String): ArrayList<DialogOptionItem>
    {
        return menuOptionsProvider.getMenuOptions(categoryId = categoryId, optionType = optionType)
    }

    fun createNewOrder()
    {
        menuOptionsProvider.createNewOrder()
    }

    fun getCurrentOrderSize(): DialogOptionItem?
    {
        return menuOptionsProvider.currentOrderSize
    }

    fun getCurrentOrderExtras(): ArrayList<DialogOptionItem>?
    {
        return menuOptionsProvider.currentOrderExtra
    }

    fun getCurrentOrderBroth(): DialogOptionItem?
    {
        return menuOptionsProvider.currentOrderBroth
    }
}