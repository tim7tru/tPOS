package com.timmytruong.timmypos.utils.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.DataUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import kotlinx.android.synthetic.main.basic_add_dialog_body.*
import kotlinx.android.synthetic.main.cancel_add_to_order_content.view.*
import kotlinx.android.synthetic.main.image_description_quantity_content.view.*
import kotlinx.android.synthetic.main.menu_item_add_dialog_title.view.*

class  BasicItemAddDialog(private val menuItemAddClickListener: MenuItemAddClickListener,
                          private val item: MenuItem): DialogFragment()
{
    private lateinit var addNumberToOrderString: String

    private lateinit var pricePerItemString: String

    private lateinit var unitCost: String

    private var quantityNumber: Int = 1

    private var newCost: Float = 0f

    private val onCancelClickListener = View.OnClickListener {
        closeFragment()
    }

    private val onPlusClickListener = View.OnClickListener {
        when (quantityNumber)
        {
            1 ->
            {
                basic_image_desc_quantity.minus_quantity_shown.visibility = View.VISIBLE

                basic_image_desc_quantity.minus_quantity_hidden.visibility = View.INVISIBLE
            }
            98 ->
            {
                basic_image_desc_quantity.plus_quantity_shown.visibility = View.INVISIBLE

                basic_image_desc_quantity.plus_quantity_hidden.visibility = View.VISIBLE
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
                basic_image_desc_quantity.minus_quantity_shown.visibility = View.INVISIBLE

                basic_image_desc_quantity.minus_quantity_hidden.visibility = View.VISIBLE
            }
            99 ->
            {
                basic_image_desc_quantity.plus_quantity_shown.visibility = View.VISIBLE

                basic_image_desc_quantity.plus_quantity_hidden.visibility = View.INVISIBLE
            }
        }
        quantityNumber--

        updateAddText()

        updateQuantityText()

        updateCosts()
    }

    private val onAddClickListener = View.OnClickListener {
        menuItemAddClickListener.onAddToOrderDialogClicked(
            DataUtils.buildOrderedItem (
                item = item,
                quantity = quantityNumber,
                unitCost = unitCost.toDouble()
            )
        )

        closeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.basic_add_dialog_body, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pricePerItemString = resources.getString(R.string.orders_dialog_price_per_item)

        addNumberToOrderString = resources.getString(R.string.orders_add_dialog)

        this.unitCost = item.cost

        setInitialText()

        setOnClickListeners()

        updateAddText()

        updateQuantityText()

        updateCosts()
    }

    private fun closeFragment()
    {
        val fragment = fragmentManager?.findFragmentByTag(AppConstants.DIALOG_FRAGMENT_TAG)

        if (fragment != null)
        {
            val dialog = fragment as DialogFragment

            dialog.dismiss()
        }
    }

    private fun setInitialText()
    {
        basic_image_desc_quantity.description_text.text = item.description

        basic_title.add_dialog_menu_item_title.text = CommonUtils.formatGeneralTitle(item.menuNumber, item.name)

        basic_image_desc_quantity.price_per_item.text = String.format(pricePerItemString, AppConstants.DECIMAL_FORMAT.format(item.cost.toFloat()))

    }

    private fun setOnClickListeners()
    {
        basic_image_desc_quantity.plus_quantity_shown.setOnClickListener(onPlusClickListener)

        basic_image_desc_quantity.minus_quantity_shown.setOnClickListener(onMinusClickListener)

        final_actions.add_dialog_positive_button.setOnClickListener(onAddClickListener)

        final_actions.add_dialog_negative_button.setOnClickListener(onCancelClickListener)
    }

    private fun updateAddText()
    {
        final_actions.add_dialog_positive_button.text = String.format(addNumberToOrderString, quantityNumber)
    }

    private fun updateQuantityText()
    {
        basic_image_desc_quantity.quantity_text.text = quantityNumber.toString()
    }

    private fun updateCosts()
    {
        newCost = unitCost.toFloat() * quantityNumber

        basic_subtotal_cost.text = CommonUtils.formatGeneralCosts(AppConstants.DECIMAL_FORMAT.format(newCost))
    }
}
