package com.timmytruong.timmypos.utils.ui

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import kotlinx.android.synthetic.main.alert_basic.view.*
import kotlinx.android.synthetic.main.alert_final_actions.view.*
import kotlinx.android.synthetic.main.alert_basic_body.view.*
import kotlinx.android.synthetic.main.alert_title.view.*

class  BasicItemAddDialog(private val context: Context,
                          private val menuItemAddClickListener: MenuItemAddClickListener,
                          private val item: MenuItem)
{
    private val titleView: View = View.inflate(context, R.layout.alert_title, null)

    private val bodyView: View = View.inflate(context, R.layout.alert_basic, null)

    private val imageDescQuantView: View = bodyView.basic_image_desc_quantity

    private val finalActionsView: View = bodyView.final_actions

    private val addNumberToOrderString: String = context.resources.getString(R.string.orders_add_dialog)

    private val pricePerItemString: String = context.resources.getString(R.string.orders_dialog_price_per_item)

    private lateinit var dialog: AlertDialog

    private lateinit var unitCost: String

    private var quantityNumber: Int = 1

    private var newCost: Float = 0f

    private val onCancelClickListener = View.OnClickListener {
        dialog.dismiss()
    }

    private val onPlusClickListener = View.OnClickListener {
        when (quantityNumber)
        {
            1 ->
            {
                imageDescQuantView.minus_quantity_shown.visibility = View.VISIBLE
                imageDescQuantView.minus_quantity_hidden.visibility = View.INVISIBLE
            }
            98 ->
            {
                imageDescQuantView.plus_quantity_shown.visibility = View.INVISIBLE
                imageDescQuantView.plus_quantity_hidden.visibility = View.VISIBLE
            }
        }
        quantityNumber++

        updateAddText()

        updateQuantityText()

        updateCosts()
    }

    private val onMinusClickListener = View.OnClickListener {
        when (quantityNumber)
        {
            2 ->
            {
                imageDescQuantView.minus_quantity_shown.visibility = View.INVISIBLE
                imageDescQuantView.minus_quantity_hidden.visibility = View.VISIBLE
            }
            99 ->
            {
                imageDescQuantView.plus_quantity_shown.visibility = View.VISIBLE
                imageDescQuantView.plus_quantity_hidden.visibility = View.INVISIBLE
            }
        }
        quantityNumber--

        updateAddText()

        updateQuantityText()

        updateCosts()
    }

    private val onAddClickListener = View.OnClickListener {
        dialog.dismiss()
        menuItemAddClickListener.onAddToOrderDialogClicked(OrderedItem(menuNumber = item.menu_id,
            name = item.name,
            size = null,
            extras = null,
            broth = null,
            quantity = quantityNumber,
            unitCost = unitCost.toDouble()))
    }

    fun setup()
    {
        this.unitCost = item.cost

        setInitialText()

        buildAlert()

        setOnClickListeners()

        updateAddText()

        updateQuantityText()

        updateCosts()
    }

    private fun buildAlert()
    {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        builder.setCustomTitle(titleView)

        builder.setView(bodyView)

        dialog = builder.create()

        dialog.show()
    }

    private fun setInitialText()
    {
        imageDescQuantView.description_text.text = item.description

        titleView.add_dialog_menu_item_title.text = CommonUtils.formatGeneralTitle(item.menu_id, item.name)

        imageDescQuantView.price_per_item.text = String.format(pricePerItemString, AppConstants.DECIMAL_FORMAT.format(item.cost.toFloat()))

    }

    private fun setOnClickListeners()
    {
        imageDescQuantView.plus_quantity_shown.setOnClickListener(onPlusClickListener)

        imageDescQuantView.minus_quantity_shown.setOnClickListener(onMinusClickListener)

        finalActionsView.add_dialog_positive_button.setOnClickListener(onAddClickListener)

        finalActionsView.add_dialog_negative_button.setOnClickListener(onCancelClickListener)
    }

    private fun updateAddText()
    {
        finalActionsView.add_dialog_positive_button.text = String.format(addNumberToOrderString, quantityNumber)
    }

    private fun updateQuantityText()
    {
        imageDescQuantView.quantity_text.text = quantityNumber.toString()
    }

    private fun updateCosts()
    {
        newCost = unitCost.toFloat() * quantityNumber

        bodyView.basic_subtotal_cost.text = CommonUtils.formatGeneralCosts(AppConstants.DECIMAL_FORMAT.format(newCost))
    }
}
