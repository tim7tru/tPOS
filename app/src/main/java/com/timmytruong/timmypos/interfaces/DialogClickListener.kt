package com.timmytruong.timmypos.interfaces

import android.view.View

interface DialogClickListener
{
    fun onAddClicked(view: View)

    fun onCancelClicked(view: View)

    fun onPlusClicked(view: View)

    fun onMinusClicked(view: View)

    fun onKeyboardDismiss(view: View)
}
