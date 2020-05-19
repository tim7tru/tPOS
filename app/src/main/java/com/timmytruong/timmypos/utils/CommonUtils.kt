package com.timmytruong.timmypos.utils

import android.content.res.Resources
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants
import java.util.*

object CommonUtils
{
    fun getCurrentDate(): String
    {
        return AppConstants.SIMPLE_DATE_FORMAT.format(Date())
    }

    fun findCategoryId(category: String): Int
    {
        return DataConstants.CATEGORY_ARRAY.indexOf(category.replace(' ', '_').toLowerCase()) + 1
    }

    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
}
