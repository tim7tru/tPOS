package com.timmytruong.timmypos.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timmytruong.timmypos.repository.MenuRepository
import com.timmytruong.timmypos.viewmodel.MenuViewModel

class MenuViewModelFactory(private val menuRepository: MenuRepository) : ViewModelProvider.Factory
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MenuViewModel(menuRepository) as T
    }

}