package com.timmytruong.timmypos.utils.ui

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.DialogClickListener
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.viewmodel.MenuViewModel

abstract class BaseAddDialog<titleDataBinding : ViewDataBinding, bodyDataBinding : ViewDataBinding>
(
        protected val item: MenuItem,
        protected var quantityNumber: ObservableInt = ObservableInt(1),
        protected var orderCost: ObservableDouble = ObservableDouble(item.cost.toDouble())
) : DialogClickListener
{
    protected lateinit var titleDataBinding: titleDataBinding

    protected lateinit var bodyDataBinding: bodyDataBinding

    private lateinit var menuViewModel: MenuViewModel

    private lateinit var animation: Animation

    private lateinit var dialog: AlertDialog

    protected abstract fun executeDataBinding()

    protected fun bindView(
            inflater: LayoutInflater,
            title: Int,
            body: Int,
            viewModel: MenuViewModel
    )
    {
        titleDataBinding = DataBindingUtil.inflate(inflater, title, null, false)

        bodyDataBinding = DataBindingUtil.inflate(inflater, body, null, false)

        menuViewModel = viewModel

        menuViewModel.createNewOrder()
    }

    protected fun setupDialog(context: Context)
    {
        val builder = AlertDialog.Builder(context)
                .setCustomTitle(titleDataBinding.root)
                .setView(bodyDataBinding.root)

        dialog = builder.create()

        dialog.show()

        dialog.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        sizeDialog()

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        animation = AnimationUtils.loadAnimation(context, R.anim.button_click_anim)
    }

    protected fun getHeight(view: TextView): Int
    {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

        return view.measuredHeight
    }

    private fun sizeDialog()
    {
        val params = dialog.window?.attributes

        params?.width = ConstraintLayout.LayoutParams.MATCH_PARENT

        params?.height = ConstraintLayout.LayoutParams.WRAP_CONTENT

        dialog.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onAddClicked(view: View)
    {
        dialog.dismiss()

        menuViewModel.addToOrder(
                OrderedItem(
                        menuNumber = item.menu_id,
                        name = item.name,
                        size = menuViewModel.getCurrentOrderSize(),
                        extras = menuViewModel.getCurrentOrderExtras(),
                        broth = menuViewModel.getCurrentOrderBroth(),
                        quantity = quantityNumber.get(),
                        unitCost = orderCost.get()
                )
        )
    }

    override fun onCancelClicked(view: View)
    {
        dialog.dismiss()
    }

    override fun onPlusClicked(view: View)
    {
        quantityNumber.set(quantityNumber.get() + 1)

        orderCost.set(item.cost.toDouble().times(quantityNumber.get()))
    }

    override fun onMinusClicked(view: View)
    {
        quantityNumber.set(quantityNumber.get() - 1)

        orderCost.set(item.cost.toDouble().times(quantityNumber.get()))
    }
}