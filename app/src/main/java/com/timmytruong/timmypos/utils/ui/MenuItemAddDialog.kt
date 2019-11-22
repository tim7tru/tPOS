package com.timmytruong.timmypos.utils.ui

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.utils.AppConstants
import com.timmytruong.timmypos.utils.CommonUtils
import org.w3c.dom.Text

class MenuItemAddDialog(private val context: Context)
{
    private val titleView: View = View.inflate(context, R.layout.menu_item_add_dialog_title, null)

    private val bodyView: View = View.inflate(context, R.layout.menu_item_add_dialog_body, null)

    private val titleTextView: TextView = titleView.findViewById(R.id.add_dialog_menu_item_title)

    private val addQuantityButtonActive: ImageView = bodyView.findViewById(R.id.plus_quantity_shown)

    private val addQuantityButtonInactive: ImageView = bodyView.findViewById(R.id.plus_quantity_hidden)

    private val quantityText: TextView = bodyView.findViewById(R.id.quantity_text)

    private val minusQuantityButtonActive: ImageView = bodyView.findViewById(R.id.minus_quantity_shown)

    private val minusQuantityButtonInactive: ImageView = bodyView.findViewById(R.id.minus_quantity_hidden)

    private val cancelButton: TextView = bodyView.findViewById(R.id.add_dialog_negative_button)

    private val addButton: TextView = bodyView.findViewById(R.id.add_dialog_positive_button)

    private val pricePerItemTextView: TextView = bodyView.findViewById(R.id.price_per_item)

    private val subtotalCostTextView: TextView = bodyView.findViewById(R.id.subtotal_cost)

    private val descriptionTextView: TextView = bodyView.findViewById(R.id.description_text)

    private val addToOrderString = context.resources.getString(R.string.orders_add_dialog)

    private val pricePerItemString = context.resources.getString(R.string.orders_dialog_price_per_item)

    private lateinit var dialog: AlertDialog

    private lateinit var unitCost: String

    private var quantityNumber: Int = 1

    fun setupDialog(title: String, description: String, cost: String)
    {
        this.unitCost = cost

        addQuantityButtonActive.setOnClickListener(onPlusClickListener)
        minusQuantityButtonActive.setOnClickListener(onMinusClickListener)

        updateCosts()
        updateAddText()
        updateQuantityText()
        descriptionTextView.text = description
        pricePerItemTextView.text = String.format(pricePerItemString, cost)
        titleTextView.text = title

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setCustomTitle(titleView)
        builder.setView(bodyView)
        dialog = builder.create()

        cancelButton.setOnClickListener(onCancelClickListener)

        dialog.show()
    }

    private fun updateAddText()
    {
        addButton.text = String.format(addToOrderString, quantityNumber)
    }

    private fun updateQuantityText()
    {
        quantityText.text = quantityNumber.toString()
    }

    private fun updateCosts()
    {
        val newCost = unitCost.toFloat() * quantityNumber
        subtotalCostTextView.text = CommonUtils.formatGeneralCosts(context, AppConstants.DECIMAL_FORMAT.format(newCost))
    }

    private val onCancelClickListener = View.OnClickListener {
        dialog.dismiss()
    }

    private val onPlusClickListener = View.OnClickListener {
        when (quantityNumber)
        {
            1 ->
            {
                minusQuantityButtonActive.visibility = View.VISIBLE
                minusQuantityButtonInactive.visibility = View.INVISIBLE
            }
            98 ->
            {
                addQuantityButtonActive.visibility = View.INVISIBLE
                addQuantityButtonInactive.visibility = View.VISIBLE
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
                minusQuantityButtonActive.visibility = View.INVISIBLE
                minusQuantityButtonInactive.visibility = View.VISIBLE
            }
            99 ->
            {
                addQuantityButtonActive.visibility = View.VISIBLE
                addQuantityButtonInactive.visibility = View.INVISIBLE
            }
        }

        quantityNumber--
        updateAddText()
        updateQuantityText()
        updateCosts()
    }

}