package com.timmytruong.timmypos.utils.constants

object DataConstants
{
    const val JSON_SUFFIX = ".json"

    const val FORWARD = "/"

    const val APPETIZERS = "appetizers"

    const val SOUPS = "soups"

    const val PHO = "pho"

    const val RICE = "rice"

    const val FRIED_RICE = "fried_rice"

    const val VERMICELLI = "vermicelli"

    const val STIR_FRY = "stir_fry"

    const val DRINK = "drinks"

    const val CONTAINS_SHRIMP_TAG = "containsShrimp"

    const val WITH_OR_WITHOUT_TAG = "withOrWithout"

    const val NODE_ERRORS = "errors"

    const val DEFAULT_REFRESH_TIME = 3 * 60 * 60 * 1000L

    val CATEGORY_ARRAY = arrayOf(
            APPETIZERS,
            SOUPS,
            PHO,
            RICE,
            FRIED_RICE,
            VERMICELLI,
            STIR_FRY,
            DRINK
    )

    val WITH_OR_WITHOUT_ARRAY = arrayOf("With Broth", "Broth On Side")



    /**
     * NODES
     */

    // MENU

    const val NODE_MENU = "menu"

    const val NODE_MENU_DIALOG_TYPE = "dialog_type"

    const val NODE_MENU_COST = "cost"

    const val NODE_MENU_AVAILABILITY = "available"

    const val NODE_MENU_NAME = "name"

    const val NODE_MENU_ID = "menu_id"

    const val NODE_MENU_CATEGORY_ID = "category_id"
    
    const val NODE_MENU_CATEGORY_NAME = "category_name"
    
    const val NODE_MENU_DESCRIPTION = "description"

    const val NODE_MENU_TAGS = "tags"

    // EXTRAS

    const val NODE_OPTIONS = "options"

    const val NODE_EXTRAS_NAME = "name"

    const val NODE_EXTRAS_COST = "cost"

    const val NODE_EXTRAS_SOUPS_EXTRAS = "soups_extras"

    const val NODE_EXTRAS_OPTION_TAG = "option_tag"

    const val NODE_EXTRAS_CATEGORY = "category"

    const val NODE_EXTRAS_CATEGORY_ID = "category_id"

    const val NODE_EXTRAS_CHECKED_STATUS = "checked_status"
}