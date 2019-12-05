package com.timmytruong.timmypos.utils.ui

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.adapters.DialogOptionItemsAdapter
import com.timmytruong.timmypos.interfaces.DialogItemClickListener
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.models.DialogOptionItem
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.CommonUtils
import kotlinx.android.synthetic.main.soups_add_dialog_body.view.*
import kotlinx.android.synthetic.main.menu_item_add_dialog_title.view.*

class SoupsItemAddDialog(private val context: Context,
                         private val menuAddItemClickListener: MenuItemAddClickListener,
                         private val tag: String)
{
    private val titleView: View = View.inflate(context, R.layout.menu_item_add_dialog_title, null)

    private val bodyView: View = View.inflate(context, R.layout.soups_add_dialog_body, null)

    private val sizesArray: ArrayList<DialogOptionItem> = arrayListOf()

    private val extrasArray: ArrayList<DialogOptionItem> = arrayListOf()

    private lateinit var dialogOptionSizesAdapter: DialogOptionItemsAdapter

    private lateinit var dialogOptionExtrasAdapter: DialogOptionItemsAdapter

    private lateinit var dialog: AlertDialog

    private lateinit var unitCost: String

    private var newUnitCost: Float = 0f

    private var sizeCost: Float = 0f

    private var extrasCost: Float = 0f

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
                bodyView.soups_minus_quantity_shown.visibility = View.VISIBLE
                bodyView.soups_minus_quantity_hidden.visibility = View.INVISIBLE
            }
            98 ->
            {
                bodyView.soups_plus_quantity_shown.visibility = View.INVISIBLE
                bodyView.soups_plus_quantity_hidden.visibility = View.VISIBLE
            }
        }
        quantityNumber++
        updateAddText()
        updateQuantityText()
        updateCosts(newUnitCost)
    }

    private val onMinusClickListener = View.OnClickListener {
        when (quantityNumber)
        {
            2 ->
            {
                bodyView.soups_minus_quantity_shown.visibility = View.INVISIBLE
                bodyView.soups_minus_quantity_hidden.visibility = View.VISIBLE
            }
            99 ->
            {
                bodyView.soups_plus_quantity_shown.visibility = View.VISIBLE
                bodyView.soups_plus_quantity_hidden.visibility = View.INVISIBLE
            }
        }
        quantityNumber--
        updateAddText()
        updateQuantityText()
        updateCosts(newUnitCost)
    }

    private val onAddClickListener = View.OnClickListener {
        dialog.dismiss()
        menuAddItemClickListener.onAddToOrderDialogClicked(quantityNumber, newCost)
    }

    private val dialogItemClickListener: DialogItemClickListener =
        object : DialogItemClickListener
        {
            override fun onItemClicked(position: Int, tag: String)
            {
                when (tag)
                {
                    AppConstants.SIZE_OPTION_TAG -> {
                        for (index in 0 until sizesArray.size)
                        {
                            if (sizesArray[index].checkedStatus && index != position)
                            {
                                sizesArray[index].checkedStatus = false
                            }
                            else if (sizesArray[index].checkedStatus && index == position)
                            {
                                sizesArray[index].checkedStatus = false
                            }
                            else if (!sizesArray[index].checkedStatus && index == position)
                            {
                                sizesArray[index].checkedStatus = true
                            }
                        }

                        dialogOptionSizesAdapter.notifyDataSetChanged()

                        sizeCost =
                            if (sizesArray[position].checkedStatus)
                            {
                                sizesArray[position].unitValue.toFloat()
                            }
                            else
                            {
                                0f
                            }

                    }
                    AppConstants.EXTRA_OPTION_TAG -> {
                        var unitValue: Float = extrasArray[position].unitValue.toFloat()

                        when (extrasArray[position].checkedStatus)
                        {
                            true -> unitValue = -unitValue
                        }

                        extrasArray[position].checkedStatus = !extrasArray[position].checkedStatus

                        dialogOptionExtrasAdapter.notifyDataSetChanged()

                        extrasCost += unitValue
                    }
                }

                newUnitCost = unitCost.toFloat() + sizeCost + extrasCost

                updateCosts(newUnitCost)
            }
        }

    fun setup(title: String, description: String, cost: String)
    {
        this.unitCost = cost

        newUnitCost = cost.toFloat()

        setInitialText(description, title)

        setAdapters()

        createSizeData()

        createExtrasData()

        buildAlert()

        setOnClickListeners()

        updateCosts(unitCost.toFloat())

        updateAddText()

        updateQuantityText()
    }

//    private fun buildOrderedItem(): OrderedItem
//    {
//        return OrderedItem(
//            quantity = quantityNumber,
//            name = titleView.add_dialog_menu_item_title.text as String,
//            subtotal = newCost.toDouble(),
//            extras =
//        )
//    }

    private fun setInitialText(description: String, title: String)
    {
        bodyView.soups_description_text.text = description

        titleView.add_dialog_menu_item_title.text = title
    }

    private fun setAdapters()
    {
        dialogOptionSizesAdapter = DialogOptionItemsAdapter(context, sizesArray, dialogItemClickListener)

        dialogOptionExtrasAdapter = DialogOptionItemsAdapter(context, extrasArray, dialogItemClickListener)

        bodyView.soups_sizes_body.layoutManager = LinearLayoutManager(context)

        bodyView.soups_extras_body.layoutManager = LinearLayoutManager(context)

        bodyView.soups_sizes_body.adapter = dialogOptionSizesAdapter

        bodyView.soups_extras_body.adapter = dialogOptionExtrasAdapter
    }

    private fun setOnClickListeners()
    {
        bodyView.soups_plus_quantity_shown.setOnClickListener(onPlusClickListener)

        bodyView.soups_minus_quantity_shown.setOnClickListener(onMinusClickListener)

        bodyView.soups_add_dialog_positive_button.setOnClickListener(onAddClickListener)

        bodyView.soups_add_dialog_negative_button.setOnClickListener(onCancelClickListener)
    }

    private fun buildAlert()
    {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)

        builder.setCustomTitle(titleView)

        builder.setView(bodyView)

        dialog = builder.create()

        dialog.show()
    }

    private fun createSizeData()
    {
        when (tag)
        {
            AppConstants.PHO_CATEGORY_TAG ->
            {
                var item = DialogOptionItem(false, context.resources.getString(R.string.size_small), 0, AppConstants.SIZE_OPTION_TAG)
                sizesArray.add(item)

                item = DialogOptionItem(false, context.resources.getString(R.string.size_large_pho), 1, AppConstants.SIZE_OPTION_TAG)
                sizesArray.add(item)

                item = DialogOptionItem(false, context.resources.getString(R.string.size_xlarge_pho), 4, AppConstants.SIZE_OPTION_TAG)
                sizesArray.add(item)
            }
            AppConstants.SOUPS_CATEGORY_TAG ->
            {
                var item = DialogOptionItem(false, context.resources.getString(R.string.size_large_soups), 0, AppConstants.SIZE_OPTION_TAG)
                sizesArray.add(item)

                item = DialogOptionItem(false, context.resources.getString(R.string.size_xlarge_soups), 4, AppConstants.SIZE_OPTION_TAG)
                sizesArray.add(item)
            }
        }

        dialogOptionSizesAdapter.notifyDataSetChanged()
    }

    private fun createExtrasData()
    {
        var item = DialogOptionItem(false, context.resources.getString(R.string.extras_noodles), 3, AppConstants.EXTRA_OPTION_TAG)
        extrasArray.add(item)

        item = DialogOptionItem(false, context.resources.getString(R.string.extras_rare_beef), 3, AppConstants.EXTRA_OPTION_TAG)
        extrasArray.add(item)

        item = DialogOptionItem(false, context.resources.getString(R.string.extras_well_done), 3, AppConstants.EXTRA_OPTION_TAG)
        extrasArray.add(item)

        item = DialogOptionItem(false, context.resources.getString(R.string.extras_beef_balls), 3, AppConstants.EXTRA_OPTION_TAG)
        extrasArray.add(item)

        item = DialogOptionItem(false, context.resources.getString(R.string.extras_tofu), 3, AppConstants.EXTRA_OPTION_TAG)
        extrasArray.add(item)

        item = DialogOptionItem(false, context.resources.getString(R.string.extras_veggies), 3, AppConstants.EXTRA_OPTION_TAG)
        extrasArray.add(item)

        item = DialogOptionItem(false, context.resources.getString(R.string.extras_chili_oil), 1, AppConstants.EXTRA_OPTION_TAG)
        extrasArray.add(item)

        item = DialogOptionItem(false, context.resources.getString(R.string.extras_pork), 3, AppConstants.EXTRA_OPTION_TAG)
        extrasArray.add(item)

        item = DialogOptionItem(false, context.resources.getString(R.string.extras_tendon), 3, AppConstants.EXTRA_OPTION_TAG)
        extrasArray.add(item)

        item = DialogOptionItem(false, context.resources.getString(R.string.extras_tripe), 3, AppConstants.EXTRA_OPTION_TAG)
        extrasArray.add(item)

        dialogOptionExtrasAdapter.notifyDataSetChanged()
    }

    private fun updateAddText()
    {
        bodyView.soups_add_dialog_positive_button.text = String.format(context.resources.getString(R.string.orders_add_dialog), quantityNumber)
    }

    private fun updateQuantityText()
    {
        bodyView.soups_quantity_text.text = quantityNumber.toString()
    }

    private fun updateCosts(cost: Float)
    {
        bodyView.soups_price_per_item.text = String.format(context.resources.getString(R.string.orders_dialog_price_per_item), AppConstants.DECIMAL_FORMAT.format(cost))

        newCost = cost * quantityNumber

        bodyView.soups_subtotal_cost.text = CommonUtils.formatGeneralCosts(AppConstants.DECIMAL_FORMAT.format(newCost))
    }


}