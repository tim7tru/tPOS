package com.timmytruong.timmypos.utils.ui

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.model.MenuItem

class SoupsItemAddDialog(
        mContext: Context,
        private val menuAddItemClickListener: MenuItemAddClickListener,
        private val categoryTitle: String,
        private val item: MenuItem,
        private val soupsExtraArray: ArrayList<DialogOptionItem>
): AlertDialog.Builder(mContext)
{
    private var titleView: View

    private var bodyView: View

    init
    {
        val inflater = LayoutInflater.from(mContext)

        bodyView = inflater.inflate(R.layout.alert_soups_body, null)

        titleView = inflater.inflate(R.layout.alert_title, null)

        setView(bodyView)

        setCustomTitle(titleView)


    }
}
//    private val titleView: View = View.inflate(context, R.layout.alert_title, null)
//
//    private val bodyView: View = View.inflate(context, R.layout.alert_soups_body, null)
//
//    private val imageDescQuantView: View = bodyView.soups_image_desc_quantity
//
//    private val finalActionsView: View = bodyView.final_actions
//
//    private val sizesArray: ArrayList<DialogOptionItem> = arrayListOf()
//
//    private val brothArray: ArrayList<DialogOptionItem> = arrayListOf()
//
//    private lateinit var dialogOptionsBrothAdapter: DialogOptionItemsAdapter
//
//    private lateinit var dialogOptionSizesAdapter: DialogOptionItemsAdapter
//
//    private lateinit var dialogOptionExtrasAdapter: DialogOptionItemsAdapter
//
//    private lateinit var dialog: AlertDialog
//
//    private lateinit var unitCost: String
//
//    private var newUnitCost: Float = 0f
//
//    private var sizeCost: Float = 0f
//
//    private var extrasCost: Float = 0f
//
//    private var quantityNumber: Int = 1
//
//    private var newCost: Float = 0f
//
//    private val onCancelClickListener = View.OnClickListener {
//        resetCheckedExtras()
//
//        dialog.dismiss()
//    }
//
//    private val onPlusClickListener = View.OnClickListener {
//        when (quantityNumber)
//        {
//            1  ->
//            {
//                imageDescQuantView.minus_quantity_shown.visibility = View.VISIBLE
//
//                imageDescQuantView.minus_quantity_hidden.visibility = View.INVISIBLE
//            }
//            98 ->
//            {
//                imageDescQuantView.plus_quantity_shown.visibility = View.INVISIBLE
//
//                imageDescQuantView.plus_quantity_hidden.visibility = View.VISIBLE
//            }
//        }
//        quantityNumber++
//
//        updateAddText()
//
//        updateQuantityText()
//
//        updateCosts(newUnitCost)
//    }
//
//    private val onMinusClickListener = View.OnClickListener {
//        when (quantityNumber)
//        {
//            2  ->
//            {
//                imageDescQuantView.minus_quantity_shown.visibility = View.INVISIBLE
//
//                imageDescQuantView.minus_quantity_hidden.visibility = View.VISIBLE
//            }
//            99 ->
//            {
//                imageDescQuantView.plus_quantity_shown.visibility = View.VISIBLE
//
//                imageDescQuantView.plus_quantity_hidden.visibility = View.INVISIBLE
//            }
//        }
//
//        quantityNumber--
//
//        updateAddText()
//
//        updateQuantityText()
//
//        updateCosts(newUnitCost)
//    }
//
//    private val onAddClickListener = View.OnClickListener {
//        dialog.dismiss()
//
//        menuAddItemClickListener.onAddToOrderDialogClicked(
//                DataUtils.buildOrderedItem(
//                        item = item,
//                        sizes = sizesArray,
//                        extras = soupsExtraArray,
//                        broths = brothArray,
//                        quantity = quantityNumber,
//                        unitCost = newUnitCost.toDouble()
//                )
//        )
//    }
//
//    private val dialogItemClickListener: DialogItemClickListener =
//            object : DialogItemClickListener
//            {
//                override fun onItemClicked(position: Int, tag: String)
//                {
//                    when (tag)
//                    {
//                        AppConstants.SIZE_OPTION_TAG  ->
//                        {
//                            for (index in 0 until sizesArray.size)
//                            {
//                                if ((sizesArray[index].checkedStatus && index != position) || (sizesArray[index].checkedStatus && index == position))
//                                {
//                                    sizesArray[index].checkedStatus = false
//                                }
//                                else if (!sizesArray[index].checkedStatus && index == position)
//                                {
//                                    sizesArray[index].checkedStatus = true
//                                }
//                            }
//
//                            dialogOptionSizesAdapter.notifyDataSetChanged()
//
//                            sizeCost =
//                                    if (sizesArray[position].checkedStatus)
//                                    {
//                                        sizesArray[position].cost.toFloat()
//                                    }
//                                    else
//                                    {
//                                        0f
//                                    }
//
//                        }
//                        AppConstants.EXTRA_OPTION_TAG ->
//                        {
//                            var unitValue: Float = soupsExtraArray[position].cost.toFloat()
//
//                            when (soupsExtraArray[position].checkedStatus)
//                            {
//                                true -> unitValue = -unitValue
//                            }
//
//                            soupsExtraArray[position].checkedStatus =
//                                    !soupsExtraArray[position].checkedStatus
//
//                            dialogOptionExtrasAdapter.notifyDataSetChanged()
//
//                            extrasCost += unitValue
//                        }
//                        AppConstants.BROTH_TAG        ->
//                        {
//                            for (index in 0 until brothArray.size)
//                            {
//                                if ((brothArray[index].checkedStatus && index != position) || (brothArray[index].checkedStatus && index == position))
//                                {
//                                    brothArray[index].checkedStatus = false
//                                }
//                                else if (!brothArray[index].checkedStatus && index == position)
//                                {
//                                    brothArray[index].checkedStatus = true
//                                }
//
//                                dialogOptionsBrothAdapter.notifyDataSetChanged()
//                            }
//                        }
//                    }
//
//                    newUnitCost = unitCost.toFloat() + sizeCost + extrasCost
//
//                    updateCosts(newUnitCost)
//                }
//            }
//
//    fun setup()
//    {
//        this.unitCost = item.cost
//
//        newUnitCost = item.cost.toFloat()
//
//        setInitialText()
//
//        setBrothOptions()
//
//        setAdapters()
//
//        createSizeData()
//
//        buildAlert()
//
//        setOnClickListeners()
//
//        updateCosts(unitCost.toFloat())
//
//        updateAddText()
//
//        updateQuantityText()
//    }
//
//    private fun setupChips()
//    {
//
//    }
//    private fun resetCheckedExtras()
//    {
//        soupsExtraArray.forEach {
//            it.resetChecked()
//        }
//    }
//
//    private fun setBrothOptions()
//    {
//        if (!item.tags.isNullOrEmpty() && item.tags.contains(DataConstants.WITH_OR_WITHOUT_TAG))
//        {
//            bodyView.broth_option.visibility = View.VISIBLE
//            for (index in DataConstants.WITH_OR_WITHOUT_ARRAY.indices)
//            {
//                brothArray.add(
//                        DialogOptionItem(
//                                name = DataConstants.WITH_OR_WITHOUT_ARRAY[index],
//                                cost = "0",
//                                optionTag = AppConstants.BROTH_TAG,
//                                categoryName = AppConstants.SOUPS_CATEGORY_TAG,
//                                categoryId = CommonUtils.findCategoryId(AppConstants.SOUPS_CATEGORY_TAG)
//                        )
//                )
//            }
//        }
//        else
//        {
//            bodyView.broth_option.visibility = View.GONE
//        }
//    }
//
//    private fun setInitialText()
//    {
//        imageDescQuantView.description_text.text = item.description
//
//        titleView.add_dialog_menu_item_title.text =
//                CommonUtils.formatGeneralTitle(item.menu_id, item.name)
//    }
//
//    private fun setAdapters()
//    {
//        dialogOptionsBrothAdapter =
//                DialogOptionItemsAdapter(context, brothArray, dialogItemClickListener)
//
//        dialogOptionSizesAdapter =
//                DialogOptionItemsAdapter(context, sizesArray, dialogItemClickListener)
//
//        dialogOptionExtrasAdapter =
//                DialogOptionItemsAdapter(context, soupsExtraArray, dialogItemClickListener)
//
//        bodyView.broth_option_body.layoutManager = LinearLayoutManager(context)
//
//        bodyView.soups_sizes_body.layoutManager = LinearLayoutManager(context)
//
//        bodyView.soups_extras_body.layoutManager = LinearLayoutManager(context)
//
//        bodyView.broth_option_body.adapter = dialogOptionsBrothAdapter
//
//        bodyView.soups_sizes_body.adapter = dialogOptionSizesAdapter
//
//        bodyView.soups_extras_body.adapter = dialogOptionExtrasAdapter
//    }
//
//    private fun setOnClickListeners()
//    {
//        imageDescQuantView.plus_quantity_shown.setOnClickListener(onPlusClickListener)
//
//        imageDescQuantView.minus_quantity_shown.setOnClickListener(onMinusClickListener)
//
//        finalActionsView.add_dialog_positive_button.setOnClickListener(onAddClickListener)
//
//        finalActionsView.add_dialog_negative_button.setOnClickListener(onCancelClickListener)
//    }
//
//    private fun buildAlert()
//    {
//        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
//
//        builder.setCustomTitle(titleView)
//
//        builder.setView(bodyView)
//
//        dialog = builder.create()
//
//        dialog.show()
//    }
//
//    private fun createSizeData()
//    {
//        when (categoryTitle)
//        {
//            AppConstants.PHO_CATEGORY_TAG   ->
//            {
//                var item = DialogOptionItem(
//                        checkedStatus = false,
//                        name = context.resources.getString(R.string.size_small),
//                        cost = "0",
//                        optionTag = AppConstants.SIZE_OPTION_TAG,
//                        categoryName = AppConstants.PHO_CATEGORY_TAG,
//                        categoryId = CommonUtils.findCategoryId(AppConstants.PHO_CATEGORY_TAG)
//                )
//                sizesArray.add(item)
//
//                item = DialogOptionItem(
//                        checkedStatus = false,
//                        name = context.resources.getString(R.string.size_large_pho),
//                        cost = "1",
//                        optionTag = AppConstants.SIZE_OPTION_TAG,
//                        categoryName = AppConstants.PHO_CATEGORY_TAG,
//                        categoryId = CommonUtils.findCategoryId(AppConstants.PHO_CATEGORY_TAG)
//                )
//                sizesArray.add(item)
//
//                item = DialogOptionItem(
//                        checkedStatus = false,
//                        name = context.resources.getString(R.string.size_xlarge_pho),
//                        cost = "4",
//                        optionTag = AppConstants.SIZE_OPTION_TAG,
//                        categoryName = AppConstants.PHO_CATEGORY_TAG,
//                        categoryId = CommonUtils.findCategoryId(AppConstants.PHO_CATEGORY_TAG)
//                )
//                sizesArray.add(item)
//            }
//            AppConstants.SOUPS_CATEGORY_TAG ->
//            {
//                var item = DialogOptionItem(
//                        checkedStatus = false,
//                        name = context.resources.getString(R.string.size_large_soups),
//                        cost = "0",
//                        optionTag = AppConstants.SIZE_OPTION_TAG,
//                        categoryName = AppConstants.SOUPS_CATEGORY_TAG,
//                        categoryId = CommonUtils.findCategoryId(AppConstants.SOUPS_CATEGORY_TAG)
//                )
//                sizesArray.add(item)
//
//                item = DialogOptionItem(
//                        checkedStatus = false,
//                        name = context.resources.getString(R.string.size_xlarge_soups),
//                        cost = "4",
//                        optionTag = AppConstants.SIZE_OPTION_TAG,
//                        categoryName = AppConstants.SOUPS_CATEGORY_TAG,
//                        categoryId = CommonUtils.findCategoryId(AppConstants.SOUPS_CATEGORY_TAG)
//                )
//                sizesArray.add(item)
//            }
//        }
//
//        dialogOptionSizesAdapter.notifyDataSetChanged()
//    }
//
//    private fun updateAddText()
//    {
//        finalActionsView.add_dialog_positive_button.text = String.format(
//                context.resources.getString(R.string.orders_add_dialog),
//                quantityNumber
//        )
//    }
//
//    private fun updateQuantityText()
//    {
//        imageDescQuantView.quantity_text.text = quantityNumber.toString()
//    }
//
//    private fun updateCosts(cost: Float)
//    {
//        imageDescQuantView.price_per_item.text = String.format(
//                context.resources.getString(R.string.orders_dialog_price_per_item),
//                AppConstants.DECIMAL_FORMAT.format(cost)
//        )
//
//        newCost = cost * quantityNumber
//
//        bodyView.soups_subtotal_cost.text =
//                CommonUtils.formatGeneralCosts(AppConstants.DECIMAL_FORMAT.format(newCost))
//    }
//
//
