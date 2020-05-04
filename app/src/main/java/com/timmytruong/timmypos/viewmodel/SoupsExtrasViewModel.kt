package com.timmytruong.timmypos.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.mapper.SoupsExtrasMapper
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.model.database.MenuDatabase
import com.timmytruong.timmypos.provider.SoupsExtrasProvider
import com.timmytruong.timmypos.repository.SoupsExtrasRepository
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.PreferenceUtils
import com.timmytruong.timmypos.utils.constants.DataConstants
import kotlinx.coroutines.launch

class SoupsExtrasViewModel(application: Application) : BaseViewModel(application)
{
    val soupsExtras = MutableLiveData<List<DialogOptionItem>>()

    private var refreshTime = DataConstants.DEFAULT_REFRESH_TIME

    private var prefUtils = PreferenceUtils(getApplication())

    private val soupsExtrasMapper = SoupsExtrasMapper()

    private val soupsProvider = SoupsExtrasProvider()

    private val soupsExtrasRepository: SoupsExtrasRepository =
            SoupsExtrasRepository(soupsExtrasMapper = soupsExtrasMapper)

    private val callback: FirebaseDatabaseRepositoryCallback<DialogOptionItem> =
            object : FirebaseDatabaseRepositoryCallback<DialogOptionItem>
            {
                override fun onSuccess(result: List<DialogOptionItem>)
                {
                    storeExtrasLocally(result)
                }

                override fun onError(e: Exception)
                {
                    soupsExtrasRepository.postValue(
                            DataConstants.NODE_ERRORS,
                            CommonUtils.getCurrentDate(),
                            e.stackTrace.toString()
                    )

                    soupsExtras.value = null
                }
            }

    override fun onCleared()
    {
        soupsExtrasRepository.removeListener()
    }

    private fun storeExtrasLocally(list: List<DialogOptionItem>)
    {
        launch {
            val dao = MenuDatabase(getApplication()).dialogOptionItemDao()

            dao.deleteAll()

            for (category in list.indices)
            {
                dao.insertAll(*list.toTypedArray())
            }

            extrasRetrievedFromFirebase(list)
        }

        prefUtils.saveUpdateTime(System.nanoTime())
    }

    private fun extrasRetrievedFromFirebase(list: List<DialogOptionItem>)
    {
        soupsExtras.value = list
    }

    fun fetch()
    {
        checkCacheDuration()

        val updateTime = prefUtils.getUpdateTime()

        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime)
        {
            loadExtrasFromDatabase()
        }
        else
        {
            loadExtrasFromFirebase()
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

    private fun loadExtrasFromFirebase()
    {
        soupsExtrasRepository.addListener(callback)
    }

    private fun loadExtrasFromDatabase()
    {
        launch {
            val extras = MenuDatabase(getApplication()).dialogOptionItemDao().getAll()

            extrasRetrievedFromFirebase(extras)
        }
    }


    fun getSoupsExtras(): ArrayList<DialogOptionItem>
    {
        return soupsProvider.getSoupsExtras()
    }

    fun onSoupsExtrasRetrieved(soupsExtras: List<DialogOptionItem>)
    {
        soupsProvider.onSoupsExtrasRetrieved(soupsExtras = soupsExtras)
    }

}