package com.timmytruong.timmypos.utils.ui

import android.app.AlertDialog
import android.content.Context
import android.view.MenuItem
import android.view.View
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import kotlinx.android.synthetic.main.basic_add_dialog_body.view.*
import kotlinx.android.synthetic.main.cancel_add_to_order_content.view.*
import kotlinx.android.synthetic.main.image_description_quantity_content.view.*
import kotlinx.android.synthetic.main.menu_item_add_dialog_title.view.*

class  BasicItemAddDialog(private val context: Context,
                          private val menuItemAddClickListener: MenuItemAddClickListener,
                          private val item: MenuItem
)
{
    private val titleView: View = View.inflate(context, R.layout.menu_item_add_dialog_title, null)

    private val bodyView: View = View.inflate(context, R.layout.basic_add_dialog_body, null)

    private val imageDescQuantView: View = bodyView.basic_description_text

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
                imageDescQuantView.basic_minus_quantity_shown.visibility = View.VISIBLE
                imageDescQuantView.basic_minus_quantity_hidden.visibility = View.INVISIBLE
            }
            98 ->
            {
                imageDescQuantView.basic_plus_quantity_shown.visibility = View.INVISIBLE
                imageDescQuantView.basic_plus_quantity_hidden.visibility = View.VISIBLE
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
                imageDescQuantView.basic_minus_quantity_shown.visibility = View.INVISIBLE
                imageDescQuantView.basic_minus_quantity_hidden.visibility = View.VISIBLE
            }
            99 ->
            {
                imageDescQuantView.basic_plus_quantity_shown.visibility = View.VISIBLE
                imageDescQuantView.basic_plus_quantity_hidden.visibility = View.INVISIBLE
            }
        }
        quantityNumber--

        updateAddText()

        updateQuantityText()

        updateCosts()
    }

    fun sockMerchant(n: Int, ar: Array<Int>): Int {
        val hashMap: HashMap<Int, Int> = hashMapOf()
        var pairCount: Int = 0
        for (index in ar)
        {
            if (!hashMap.keys.contains(index))
            {
                hashMap.put(index, 1)
            }
            else if (hashMap.isNotEmpty() && hashMap.containsKey(index))
            {
                val plus: Int = hashMap[index]!!.plus(1)
                hashMap.put(index, hashMap[index]!! + 1)
                if (hashMap[index]!!.rem(2) == 0)
                {
                    pairCount++
                }
            }
        }

        return pairCount

    }

    private val onAddClickListener = View.OnClickListener {
        dialog.dismiss()
        menuItemAddClickListener.onAddToOrderDialogClicked(OrderedItem(
            menuNumber = item.menuNumber,
            name = item.name,
            quantity = quantityNumber,
            unitCost = unitCost.toDouble()
        ))
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

        titleView.add_dialog_menu_item_title.text = CommonUtils.formatGeneralTitle(item.menuNumber, item.name)

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
