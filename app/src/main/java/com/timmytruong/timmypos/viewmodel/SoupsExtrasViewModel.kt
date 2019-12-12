package com.timmytruong.timmypos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.repository.SoupsExtrasRepository
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.DataConstants
import javax.inject.Inject

class SoupsExtrasViewModel @Inject constructor(private val soupsExtrasRepository: SoupsExtrasRepository): ViewModel()
{
    private var soupsExtras: MutableLiveData<List<DialogOptionItem>>? = null

    private var soupsExtrasArray: ArrayList<DialogOptionItem> = arrayListOf()

    private val callback: FirebaseDatabaseRepositoryCallback<DialogOptionItem> =
        object: FirebaseDatabaseRepositoryCallback<DialogOptionItem>
        {
            override fun onSuccess(result: List<DialogOptionItem>)
            {
                soupsExtras!!.value = result
            }

            override fun onError(e: Exception)
            {
                soupsExtrasRepository.postValue(DataConstants.ERRORS_NODE, CommonUtils.getCurrentDate(), e.stackTrace.toString())

                soupsExtras!!.value = null
            }
        }

    fun getExtras(): LiveData<List<DialogOptionItem>>?
    {
        if (soupsExtras == null)
        {
            soupsExtras = MutableLiveData()

            loadExtras()
        }
        return soupsExtras
    }

    private fun loadExtras()
    {
        soupsExtrasRepository.addListener(callback)
    }

    fun addSoupExtra(item: DialogOptionItem)
    {
        soupsExtrasArray.add(item)
    }

    fun clearSoupExtraArray()
    {
        soupsExtrasArray.clear()
    }

    fun setSoupExtra(array: ArrayList<DialogOptionItem>)
    {
        soupsExtrasArray = array
    }

    fun getSoupExtras(): ArrayList<DialogOptionItem>
    {
        return soupsExtrasArray
    }
}