package com.timmytruong.timmypos.interfaces

import android.view.View

interface MenuCategoryEventListener
{
    fun onCategoryMenuItemClicked(view: View, oldPosition: Int, newPosition: Int)
}