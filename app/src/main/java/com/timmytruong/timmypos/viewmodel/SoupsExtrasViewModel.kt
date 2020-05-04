package com.timmytruong.timmypos.viewmodel

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.mapper.SoupsExtrasMapper
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.provider.SoupsExtrasProvider
import com.timmytruong.timmypos.repository.SoupsExtrasRepository
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.DataConstants

class SoupsExtrasViewModel(application: Application) : BaseViewModel(application)
{
    private var soupsExtras = MutableLiveData<List<DialogOptionItem>>()

    private val soupsExtrasMapper = SoupsExtrasMapper()

    private val soupsProvider = SoupsExtrasProvider()

    private val soupsExtrasRepository: SoupsExtrasRepository =
            SoupsExtrasRepository(soupsExtrasMapper = soupsExtrasMapper)

    private val callback: FirebaseDatabaseRepositoryCallback<DialogOptionItem> =
            object : FirebaseDatabaseRepositoryCallback<DialogOptionItem>
            {
                override fun onSuccess(result: List<DialogOptionItem>)
                {
                    soupsExtras.value = result
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

    fun getExtras(): LiveData<List<DialogOptionItem>>
    {
        loadExtras()

        return soupsExtras
    }

    fun getExtras(assets: AssetManager)
    {
        soupsExtrasRepository.getSoupsExtrasDataFromAssets(assets = assets)
    }

    private fun loadExtras()
    {
        soupsExtrasRepository.addListener(callback)
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