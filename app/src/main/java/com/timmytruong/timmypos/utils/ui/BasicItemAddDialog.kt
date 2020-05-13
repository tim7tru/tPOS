package com.timmytruong.timmypos.utils.ui

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableDouble
import androidx.databinding.ObservableInt
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.databinding.AlertBasicBinding
import com.timmytruong.timmypos.databinding.AlertTitleBinding
import com.timmytruong.timmypos.interfaces.DialogCallback
import com.timmytruong.timmypos.interfaces.DialogClickListener
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem

class BasicItemAddDialog(
        private val context: Context,
        private val item: MenuItem,
        private val height: Int,
        private val width: Int,
        private val dialogCallback: DialogCallback
) : DialogClickListener
{
    private val animation = AnimationUtils.loadAnimation(context, R.anim.button_click_anim)

    private var titleView: AlertTitleBinding

    private var bodyView: AlertBasicBinding

    private var quantityNumber: ObservableInt = ObservableInt(1)

    private lateinit var dialog: AlertDialog

    private var orderCost: ObservableDouble = ObservableDouble(item.cost.toDouble())

    init
    {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        titleView = DataBindingUtil.inflate(inflater, R.layout.alert_title, null, false)

        bodyView = DataBindingUtil.inflate(inflater, R.layout.alert_basic, null, false)

        setupDialog()

    }

    private fun setupDialog()
    {
        val builder = AlertDialog.Builder(context)
                .setCustomTitle(titleView.root)
                .setView(bodyView.root)

        dialog = builder.create()

        dialog.show()

        dialog.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)

        dialog.window?.setLayout(width, height)

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        executeDataBinding()

        resizeContent()

    }

    private fun executeDataBinding()
    {
        titleView.item = item

        bodyView.item = item

        bodyView.quantity = quantityNumber

        bodyView.cost = orderCost

        bodyView.listener = this

        bodyView.finalActions.listener = this

        bodyView.finalActions.quantity = quantityNumber
    }

    private fun resizeContent()
    {
        bodyView.quantityText.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

        bodyView.finalActions.addDialogPositiveButton.measure(
                View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED
        )

        titleView.addDialogMenuItemTitle.measure(
                View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED
        )

        val quantity = bodyView.quantityText.measuredHeight

        val actions = bodyView.finalActions.addDialogPositiveButton.measuredHeight

        val title = titleView.addDialogMenuItemTitle.measuredHeight

        val newImageDimensions = height - (1.5 * (title + quantity + actions)).toInt()

        bodyView.foodPicture.layoutParams =
                ConstraintLayout.LayoutParams(newImageDimensions, newImageDimensions)
    }

    override fun onPlusClicked(view: View)
    {
        if (quantityNumber.get() < 99)
        {
            when (quantityNumber.get())
            {
                1    ->
                {
                    bodyView.minusQuantityShown.visibility = View.VISIBLE

                    bodyView.minusQuantityHidden.visibility = View.GONE
                }
                98   ->
                {
                    bodyView.plusQuantityShown.visibility = View.GONE

                    bodyView.plusQuantityHidden.visibility = View.VISIBLE
                }
                else ->
                {
                    bodyView.plusQuantityShown.startAnimation(animation)
                }
            }
            quantityNumber.set(quantityNumber.get() + 1)

            orderCost.set((item.cost.toDouble()).times(quantityNumber.get()))
        }
    }

    override fun onMinusClicked(view: View)
    {
        if (quantityNumber.get() > 0)
        {
            when (quantityNumber.get())
            {
                2    ->
                {
                    bodyView.minusQuantityShown.visibility = View.GONE

                    bodyView.minusQuantityHidden.visibility = View.VISIBLE
                }
                99   ->
                {
                    bodyView.plusQuantityShown.visibility = View.VISIBLE

                    bodyView.plusQuantityHidden.visibility = View.GONE
                }
                else ->
                {
                    bodyView.minusQuantityShown.startAnimation(animation)
                }
            }
            quantityNumber.set(quantityNumber.get() - 1)

            orderCost.set((item.cost.toDouble()).times(quantityNumber.get()))
        }
    }

    override fun onAddClicked(view: View)
    {
        dialog.dismiss()

        dialogCallback.onAddToOrderClicked(
                OrderedItem(
                        menuNumber = item.menu_id,
                        name = item.name,
                        size = null,
                        extras = null,
                        broth = null,
                        quantity = quantityNumber.get(),
                        unitCost = orderCost.get()
                )
        )
    }

    override fun onCancelClicked(view: View)
    {
        dialog.dismiss()
    }
}