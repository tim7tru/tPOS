package com.timmytruong.timmypos.provider

import com.timmytruong.timmypos.model.CategoryMenuItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.Order
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.AppConstants

class MenuProvider(
        private var categoryItemsArray: ArrayList<MenuItem> = arrayListOf(),

        private val categoryTitlesArray: ArrayList<CategoryMenuItem> = arrayListOf(),

        private val order: Order = Order(),

        private var currentCategoryTitle: String = ""
)
{
    private lateinit var testItemTitle: String

    private lateinit var testItemDescription: String

    private lateinit var testItemCost: String

    init
    {
        createDummyTexts()

        createCategoryTitles()
    }

    fun getCurrenCategoryTitle(): String
    {
        return currentCategoryTitle
    }

    fun setCurrentCategoryTitle(title: String)
    {
        currentCategoryTitle = title
    }

    fun getOrder(): Order
    {
        return order
    }

    fun getCategoryTitles(): ArrayList<CategoryMenuItem>
    {
        return categoryTitlesArray
    }

    fun createCategoryTitles()
    {
        for (index in AppConstants.MENU_CATEGORY_ARRAY.indices)
        {
            categoryTitlesArray.add(
                    CategoryMenuItem(
                            id = index,
                            name = AppConstants.MENU_CATEGORY_ARRAY[index],
                            isActive = false
                    )
            )
        }

        categoryTitlesArray[0].isActive = true
    }

    fun getCategoryItems(activeCategory: String): ArrayList<MenuItem>
    {
        val category = activeCategory.replace(' ', '_').toLowerCase()

        val list =
                categoryItemsArray.filter { it.category_id == CommonUtils.findCategoryId(category) }

        return ArrayList(list)
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
        categoryTitlesArray[oldPosition].isActive = false

        categoryTitlesArray[newPosition].isActive = true

        setCurrentCategoryTitle(categoryTitlesArray[newPosition].name)
    }

    fun addToOrder(orderedItem: OrderedItem)
    {
        order.orderedItems.add(orderedItem)

        order.itemCount += orderedItem.quantity
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