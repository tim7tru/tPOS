package com.timmytruong.timmypos.provider

import com.timmytruong.timmypos.model.CategoryMenuItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.utils.constants.AppConstants

class MenuProvider(
    private val orderedItemsArray: ArrayList<OrderedItem> = arrayListOf(),
    private var categoryItemsArray: ArrayList<MenuItem> = arrayListOf(),
    private val categoryTitlesArray: ArrayList<CategoryMenuItem> = arrayListOf(),
    private val categoryArrays: ArrayList<ArrayList<MenuItem>> = arrayListOf(),
    private var itemCount: Int = 0,
    private var currentCategoryTitle: String = ""
) {

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

    fun getCategoryItems(): ArrayList<MenuItem>
    {
        return categoryItemsArray
    }

    fun createCategoryData() {
        var activeState = true

        for (index in AppConstants.MENU_CATEGORY_ARRAY) {
            val item = CategoryMenuItem(index, activeState)

            activeState = false

            categoryTitlesArray.add(item)
        }

        currentCategoryTitle = categoryTitlesArray[0].title
    }

    fun onMenuRetrieved(menu: List<ArrayList<MenuItem>>)
    {
        categoryArrays.clear()

        for (index in menu.indices)
        {
            if (menu[index].isNotEmpty())
            {
                categoryArrays.add(ArrayList(menu[index]))
            }
        }

        /**
         * Dummy Data
         */

        categoryArrays.add(createData(15))

        categoryArrays.add(createData(14))

        categoryArrays.add(createData(3))

        categoryArrays.add(createData(9))

        categoryArrays.add(createData(9))

        categoryArrays.add(createData(22))

        categoryItemsArray.addAll(categoryArrays[0])
    }

    fun onCategoryMenuItemClicked(oldPosition: Int, newPosition: Int)
    {
        categoryTitlesArray[oldPosition].activeState = false

        categoryTitlesArray[newPosition].activeState = true

        setCurrentCategoryTitle(categoryTitlesArray[newPosition].title)

        categoryItemsArray = categoryArrays[newPosition]
    }

    fun addToOrder(orderedItem: OrderedItem)
    {
        orderedItemsArray.add(orderedItem)
        itemCount += orderedItem.quantity
    }

    private fun createData(count: Int): ArrayList<MenuItem>
    {
        val array: ArrayList<MenuItem> = arrayListOf()

        for (itemCount in 0 until count)
        {
            val item = MenuItem(99, true, testItemCost, testItemDescription, "basic", testItemTitle, null)

            array.add(item)
        }
        return array
    }

    private fun createDummyTexts()
    {
        testItemTitle = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore"
        testItemDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
        testItemCost = "10.00"
    }
}