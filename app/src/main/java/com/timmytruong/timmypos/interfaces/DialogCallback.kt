package com.timmytruong.timmypos.interfaces

import com.timmytruong.timmypos.model.OrderedItem

interface DialogCallback
{
    fun onAddToOrderClicked(orderedItem: OrderedItem)
}