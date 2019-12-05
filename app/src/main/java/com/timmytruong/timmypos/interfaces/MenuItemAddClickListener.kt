package com.timmytruong.timmypos.interfaces

import android.view.View

interface MenuItemAddClickListener
{
    fun onAddToOrderButtonClicked(view: View, position: Int)

    fun onAddToOrderDialogClicked(newlyAddedItems: Int, subTotal: Float)
}