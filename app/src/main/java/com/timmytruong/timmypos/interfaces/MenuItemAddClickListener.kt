package com.timmytruong.timmypos.interfaces

import android.view.View
import com.timmytruong.timmypos.model.OrderedItem

interface MenuItemAddClickListener
{
    fun onAddToOrderButtonClicked(view: View, position: Int)

    fun onAddToOrderDialogClicked(orderedItem: OrderedItem)
}