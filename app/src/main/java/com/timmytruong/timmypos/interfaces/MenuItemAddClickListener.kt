package com.timmytruong.timmypos.interfaces

import android.content.Context
import android.view.View

interface MenuItemAddClickListener
{
    fun onAddToOrderButtonClicked(view: View, position: Int)

    fun onAddToOrderDialogClicked(newlyAddedItems: Int, subTotal: Float)
}