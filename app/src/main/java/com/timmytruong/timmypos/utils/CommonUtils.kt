package com.timmytruong.timmypos.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.timmytruong.timmypos.utils.CommonUtils.hideKeyboard
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

    fun Fragment.hideKeyboard()
    {
        view?.let { activity?.hideKeyboard(it)}
    }

    fun Activity.hideKeyboard()
    {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View)
    {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
