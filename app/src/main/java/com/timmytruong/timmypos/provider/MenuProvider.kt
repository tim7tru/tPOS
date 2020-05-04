package com.timmytruong.timmypos.provider

import com.timmytruong.timmypos.model.CategoryMenuItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants
import java.util.*
import kotlin.collections.ArrayList

class MenuProvider(
        private val orderedItemsArray: ArrayList<OrderedItem> = arrayListOf(),

        private var categoryItemsArray: ArrayList<MenuItem> = arrayListOf(),

        private val categoryTitlesArray: ArrayList<CategoryMenuItem> = arrayListOf(),

        private var itemCount: Int = 0,

        private var currentCategoryTitle: String = ""
)
{
    private lateinit var testItemTitle: String

    private lateinit var testItemDescription: String

    private lateinit var testItemCost: String

    init
    {
        createDummyTexts()
    }

    fun getCurrenCategoryTitle(): String
    {
        return currentCategoryTitle
    }

    private fun setCurrentCategoryTitle(title: String)
    {
        currentCategoryTitle = title
    }

    fun getItemCount(): Int
    {
        return itemCount
    }

    fun getCategoryTitles(): ArrayList<CategoryMenuItem>
    {
        return categoryTitlesArray
    }

    fun getCategoryItems(activeCategory: String): ArrayList<MenuItem>
    {
        val category = activeCategory.replace(' ', '_').toLowerCase()

        val list = categoryItemsArray.filter {  it.category_id == DataConstants.CATEGORY_ARRAY.indexOf(category) + 1}

        return ArrayList(list)
    }

    fun createCategoryData()
    {
        var activeState = true

        for (index in AppConstants.MENU_CATEGORY_ARRAY)
        {
            val item = CategoryMenuItem(index, activeState)

            activeState = false

            categoryTitlesArray.add(item)
        }

        currentCategoryTitle = categoryTitlesArray[0].title
    }

    fun onMenuRetrieved(menu: List<MenuItem>)
    {
        categoryItemsArray.clear()

        categoryItemsArray.addAll(menu)

        /**
         * Dummy Data
         */

        categoryItemsArray.addAll(createData())
    }

    fun onCategoryMenuItemClicked(oldPosition: Int, newPosition: Int)
    {
        categoryTitlesArray[oldPosition].activeState = false

        categoryTitlesArray[newPosition].activeState = true

        setCurrentCategoryTitle(categoryTitlesArray[newPosition].title)
    }

    fun addToOrder(orderedItem: OrderedItem)
    {
        orderedItemsArray.add(orderedItem)
        itemCount += orderedItem.quantity
    }

    private fun createData(): ArrayList<MenuItem>
    {
        val list: ArrayList<MenuItem> = arrayListOf()

        var menu_id_start = categoryItemsArray.size + 1

        val dummyQuantity = arrayListOf(15, 14, 3, 9, 9, 22)

        val dummyCategoryId = arrayListOf(3, 4, 5, 6, 7, 8)

        val dummyCategoryName =
                arrayListOf("pho", "rice", "fried_rice", "vermicelli", "stir_fry", "drinks")

        for (i in 0..5)
        {
            for (j in 0..dummyQuantity[i])
            {
                list.add(
                        MenuItem(
                                menu_id = menu_id_start,
                                category_id = dummyCategoryId[i],
                                category_name = dummyCategoryName[i],
                                availablity = true,
                                name = testItemTitle,
                                description = testItemDescription,
                                cost = testItemCost,
                                dialog_type = "basic",
                                tags = listOf()
                        )
                )
                menu_id_start++
            }

        }

        return list
    }

    private fun createDummyTexts()
    {
        testItemTitle =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore"
        testItemDescription =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
        testItemCost = "10.00"
    }
}