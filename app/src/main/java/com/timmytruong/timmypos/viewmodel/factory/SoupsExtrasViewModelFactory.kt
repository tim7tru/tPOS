package com.timmytruong.timmypos.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timmytruong.timmypos.repository.SoupsExtrasRepository
import com.timmytruong.timmypos.viewmodel.SoupsExtrasViewModel

class SoupsExtrasViewModelFactory(private val soupsExtrasRepository: SoupsExtrasRepository): ViewModelProvider.Factory
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SoupsExtrasViewModel(soupsExtrasRepository) as T
    }

}