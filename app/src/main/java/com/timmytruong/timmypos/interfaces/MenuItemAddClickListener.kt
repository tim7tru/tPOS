package com.timmytruong.timmypos.interfaces

import android.view.View
import com.timmytruong.timmypos.model.MenuItem

interface MenuItemAddClickListener
{
    fun onAddToOrderButtonClicked(view: View, item: MenuItem)
}