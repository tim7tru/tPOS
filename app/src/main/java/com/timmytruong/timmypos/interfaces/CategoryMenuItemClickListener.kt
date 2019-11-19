package com.timmytruong.timmypos.interfaces

import android.view.View

interface CategoryMenuItemClickListener
{
    fun onCategoryMenuItemClicked(view: View, newPosition: Int)

    fun getActiveColour(): Int

    fun getInactiveColour(): Int
}