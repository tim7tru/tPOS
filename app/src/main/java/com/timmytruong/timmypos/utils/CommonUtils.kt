package com.timmytruong.timmypos.utils

import android.content.Context
import com.timmytruong.timmypos.R

object CommonUtils
{
    fun formatGeneralCosts(context: Context, cost: Float)
    {
        formatGeneralCosts(context, cost.toString())
    }

    fun formatGeneralCosts(context: Context, cost: String): String
    {
        return String.format(context.resources.getString(R.string.general_cost_format), cost)
    }


}