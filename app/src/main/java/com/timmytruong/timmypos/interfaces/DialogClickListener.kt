package com.timmytruong.timmypos.interfaces

import android.view.View

interface DialogClickListener
{
    fun onPlusClicked(view: View)

    fun onMinusClicked(view: View)

    fun onAddClicked(view: View)

    fun onCancelClicked(view: View)
}