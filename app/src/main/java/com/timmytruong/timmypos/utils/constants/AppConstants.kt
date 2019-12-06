package com.timmytruong.timmypos.utils.constants

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.SimpleDateFormat

object AppConstants
{
    const val ORDERS_FRAGMENT_TAG = "orders_fragment_tag"

    const val HISTORY_FRAGMENT_TAG = "history_fragment_tag"

    const val FINANCIAL_FRAGMENT_TAG = "financial_fragment_tag"

    const val HOME_CATEGORY_TAG = "Home"

    const val APPETIZERS_CATEGORY_TAG = "Appetizers"

    const val SOUPS_CATEGORY_TAG = "Soups"

    const val PHO_CATEGORY_TAG = "Pho"

    const val RICE_CATEGORY_TAG = "Rice"

    const val FRIED_RICE_CATEGORY_TAG = "Fried Rice"

    const val VERMICELLI_CATEGORY_TAG = "Vermicelli"

    const val STIR_FRY_CATEGORY_TAG = "Stir-Fry"

    const val DRINKS_CATEGORY_TAG = "Drinks"

    const val SIZE_OPTION_TAG = "size"

    const val EXTRA_OPTION_TAG = "extra"

    val DECIMAL_FORMAT = DecimalFormat("0.00")

    val MENU_CATEGORY_ARRAY = arrayOf (
        APPETIZERS_CATEGORY_TAG,
        SOUPS_CATEGORY_TAG,
        PHO_CATEGORY_TAG,
        RICE_CATEGORY_TAG,
        FRIED_RICE_CATEGORY_TAG,
        VERMICELLI_CATEGORY_TAG,
        STIR_FRY_CATEGORY_TAG,
        DRINKS_CATEGORY_TAG
    )

    @SuppressLint("SimpleDateFormat")
    val SIMPLE_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    const val FIREBASE_EXCEPTION_LOG_TAG = "FIREBASE EXCEPTION"

    const val SOUPS_DIALOG_TYPE = "soups"

    const val BASIC_DIALOG_TYPE = "basic"
}