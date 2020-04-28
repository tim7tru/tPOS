package com.timmytruong.timmypos.provider

import com.timmytruong.timmypos.model.CategoryMenuItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem

class MenuProvider(
    val orderedItemsArray: ArrayList<OrderedItem> = arrayListOf(),
    var categoryItemsArray: ArrayList<MenuItem> = arrayListOf(),
    val categoryTitlesArray: ArrayList<CategoryMenuItem> = arrayListOf(),
    val categoryArrays: ArrayList<ArrayList<MenuItem>> = arrayListOf()
)
{

}