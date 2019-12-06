package com.timmytruong.timmypos.utils.ui

import android.app.AlertDialog
import android.content.Context
import android.view.View
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import kotlinx.android.synthetic.main.basic_add_dialog_body.view.*
import kotlinx.android.synthetic.main.cancel_add_to_order_content.view.*
import kotlinx.android.synthetic.main.image_description_quantity_content.view.*
import kotlinx.android.synthetic.main.menu_item_add_dialog_title.view.*

class BasicItemAddDialog(private val context: Context,
                         private val menuItemAddClickListener: MenuItemAddClickListener)
{
    private val titleView: View = View.inflate(context, R.layout.menu_item_add_dialog_title, null)

    private val bodyView: View = View.inflate(context, R.layout.basic_add_dialog_body, null)

    private val imageDescQuantView: View = bodyView.basic_image_desc_quantity

    private val finalActionsView: View = bodyView.final_actions

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
    }

    private val onAddClickListener = View.OnClickListener {
        dialog.dismiss()
        menuItemAddClickListener.onAddToOrderDialogClicked(quantityNumber, newCost)
    }

    fun setup(title: String, description: String, cost: String)
    {
        this.unitCost = cost

    }

    private fun setInitialText(description: String, title: String)
    {
        imageDescQuantView.description_text.text = description

        titleView.add_dialog_menu_item_title.text = title
    }

    private fun setOnClickListeners()
    {
        imageDescQuantView.plus_quantity_shown.setOnClickListener(onPlusClickListener)

        imageDescQuantView.minus_quantity_shown.setOnClickListener(onMinusClickListener)

        finalActionsView.add_dialog_positive_button.setOnClickListener(onAddClickListener)

        finalActionsView.add_dialog_negative_button.setOnClickListener(onCancelClickListener)

    }
}
