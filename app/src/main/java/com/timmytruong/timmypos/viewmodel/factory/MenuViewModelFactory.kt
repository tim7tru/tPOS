package com.timmytruong.timmypos.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.timmytruong.timmypos.viewmodel.MenuViewModel

class MenuViewModelFactory(): ViewModelProvider.NewInstanceFactory()
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MenuViewModel() as T
    }
}