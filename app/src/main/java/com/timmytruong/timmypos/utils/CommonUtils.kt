package com.timmytruong.timmypos.utils

import android.content.Context
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.utils.constants.AppConstants
import java.util.*

object CommonUtils
{
    private lateinit var generalCostFormat: String

    private lateinit var generalTitleFormat: String

    private lateinit var dialogExtraFormat: String

    private lateinit var generalStringFormat: String

    private lateinit var dialogSizeFormat: String

    private lateinit var itemCountFormat: String

    fun initializeCommonUtils(context: Context)
    {
        generalCostFormat = context.resources.getString(R.string.general_cost_format)

        generalTitleFormat = context.resources.getString(R.string.general_menu_item_title_format)

        dialogExtraFormat = context.resources.getString(R.string.general_extras_string_format)

        generalStringFormat = context.resources.getString(R.string.general_string_format)

        dialogSizeFormat = context.resources.getString(R.string.general_sizes_string_format)

        itemCountFormat = context.resources.getString(R.string.orders_item_count)
    }

    fun formatGeneralString(string: String): String
    {
        return String.format(generalStringFormat, string)
    }

    fun formatGeneralTitle(itemNumber: Int, itemName: String): String
    {
        return String.format(generalTitleFormat, itemNumber.toString(), itemName)
    }

    fun formatGeneralCosts(cost: Float)
    {
        formatGeneralCosts(cost.toString())
    }

    fun formatGeneralCosts(cost: String): String
    {
        return String.format(generalCostFormat, cost)
    }

    fun getCurrentDate(): String
    {
        return AppConstants.SIMPLE_DATE_FORMAT.format(Date())
    }

    fun formatDialogExtrasTitle(name: String, cost: String): String
    {
        return String.format(dialogExtraFormat, name, cost)
    }

    fun formatDialogSizesTitle(name: String, cost: String): String
    {
        return String.format(dialogSizeFormat, name, cost)
    }

    fun formatItemCount(count: String): String
    {
        return String.format(itemCountFormat, count)
    }
}