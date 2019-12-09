package com.timmytruong.timmypos.utils

import android.content.Context
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.utils.constants.AppConstants
import java.util.*

object CommonUtils
{
    lateinit var generalCostFormat: String
    lateinit var generalTitleFormat: String
    lateinit var dialogCostTitleNonZeroFormat: String
    lateinit var generalStringFormat: String

    fun initializeCommonUtils(context: Context)
    {
        generalCostFormat = context.resources.getString(R.string.general_cost_format)
        generalTitleFormat = context.resources.getString(R.string.general_menu_item_title_format)
        dialogCostTitleNonZeroFormat = context.resources.getString(R.string.general_extras_string_format)
        generalStringFormat = context.resources.getString(R.string.general_string_format)
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
        return String.format(dialogCostTitleNonZeroFormat, name, cost)
    }
}