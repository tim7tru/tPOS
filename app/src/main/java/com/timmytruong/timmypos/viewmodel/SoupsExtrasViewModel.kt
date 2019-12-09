package com.timmytruong.timmypos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.models.DialogOptionItem
import com.timmytruong.timmypos.repository.SoupsExtrasRepository
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.DataConstants

class SoupsExtrasViewModel: ViewModel()
{
    private var soupsExtras: MutableLiveData<List<DialogOptionItem>>? = null
    private var soupsExtrasRepository: SoupsExtrasRepository = SoupsExtrasRepository()

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
}