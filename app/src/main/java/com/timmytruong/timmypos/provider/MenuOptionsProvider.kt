package com.timmytruong.timmypos.provider

import android.app.Dialog
import com.timmytruong.timmypos.model.DialogOptionItem

class MenuOptionsProvider(
        private val options: ArrayList<DialogOptionItem> = arrayListOf()
)
{
     var currentOrderSize: DialogOptionItem? = DialogOptionItem()

     var currentOrderExtra: ArrayList<DialogOptionItem>? = arrayListOf()

     var currentOrderBroth: DialogOptionItem? = DialogOptionItem()

    fun getMenuOptions(categoryId: Int, optionType: String): ArrayList<DialogOptionItem>
    {
        return ArrayList(options.filter { it.categoryId == categoryId && it.optionTag == optionType})
    }

    fun onSoupsExtrasRetrieved(newOptions: List<DialogOptionItem>)
    {
        options.clear()

        for (index in newOptions.indices)
        {
            options.add(newOptions[index])
        }
    }

    fun createNewOrder()
    {
        currentOrderSize = DialogOptionItem()

        currentOrderExtra = arrayListOf()

        currentOrderBroth = DialogOptionItem()
    }
}
