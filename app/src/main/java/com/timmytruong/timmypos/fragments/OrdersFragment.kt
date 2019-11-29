package com.timmytruong.timmypos.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.adapters.CategoryMenuAdapter
import com.timmytruong.timmypos.adapters.MenuItemAdapter
import com.timmytruong.timmypos.interfaces.CategoryMenuItemClickListener
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.models.CategoryMenuItem
import com.timmytruong.timmypos.models.MenuItem
import com.timmytruong.timmypos.utils.ui.SoupsItemAddDialog
import kotlinx.android.synthetic.main.fragment_orders.*

class OrdersFragment : Fragment()
{
    private lateinit var appetizersString: String

    private lateinit var soupsString: String

    private lateinit var phoString: String

    private lateinit var riceString: String

    private lateinit var friedRiceString: String

    private lateinit var vermicelliString: String

    private lateinit var stirFryString: String

    private lateinit var drinksString: String

    private lateinit var testItemTitle: String

    private lateinit var testItemDescription: String

    private lateinit var testItemCost: String

    private var categoryItemsArray: ArrayList<MenuItem> = arrayListOf()

    private val categoryTitlesArray: ArrayList<CategoryMenuItem> = arrayListOf()

    private val categoryArrays: ArrayList<ArrayList<MenuItem>> = arrayListOf()

    private lateinit var categoryMenuAdapter: CategoryMenuAdapter

    private lateinit var menuAdapter: MenuItemAdapter

    private lateinit var soupsItemAddDialog: SoupsItemAddDialog

    private var itemCount: Int = 0

    private var subtotal: Float = 0f

    private var categoryTitle: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        appetizersString = resources.getString(R.string.orders_appetizers)

        soupsString = resources.getString(R.string.orders_soups)

        phoString = resources.getString(R.string.orders_pho)

        riceString = resources.getString(R.string.orders_rice)

        friedRiceString = resources.getString(R.string.orders_fried_rice)

        vermicelliString = resources.getString(R.string.orders_vermicelli)

        stirFryString = resources.getString(R.string.orders_stir_fry)

        drinksString = resources.getString(R.string.orders_drinks)

        testItemTitle = resources.getString(R.string.test_item_title)

        testItemDescription = resources.getString(R.string.test_item_description)

        testItemCost = resources.getString(R.string.test_item_cost)

        item_count.text = String.format(resources.getString(R.string.orders_item_count, itemCount.toString()))

        categoryMenuAdapter = CategoryMenuAdapter(activity!!.applicationContext, categoryTitlesArray, categoryMenuItemClickListener)

        category_menu.layoutManager = LinearLayoutManager(activity!!)

        category_menu.adapter = categoryMenuAdapter

        categoryTitle = appetizersString

        createFakeListData()

        createCategoryData()
    }

    private fun createFakeListData()
    {
        categoryArrays.add(createData(9))

        categoryArrays.add(createData(9))

        categoryArrays.add(createData(15))

        categoryArrays.add(createData(14))

        categoryArrays.add(createData(3))

        categoryArrays.add(createData(9))

        categoryArrays.add(createData(9))

        categoryArrays.add(createData(22))

        categoryItemsArray = categoryArrays[0]

        menuAdapter = MenuItemAdapter(activity!!.applicationContext, categoryItemsArray, menuItemAddClickListener)

        menu_items.layoutManager = LinearLayoutManager(activity!!)

        menu_items.adapter = menuAdapter

        menuAdapter.notifyDataSetChanged()
    }

    private fun createCategoryData()
    {
        var item = CategoryMenuItem(appetizersString, true)
        categoryTitlesArray.add(item)

        item = CategoryMenuItem(soupsString, false)
        categoryTitlesArray.add(item)

        item = CategoryMenuItem(phoString, false)
        categoryTitlesArray.add(item)

        item = CategoryMenuItem(riceString, false)
        categoryTitlesArray.add(item)

        item = CategoryMenuItem(friedRiceString, false)
        categoryTitlesArray.add(item)

        item = CategoryMenuItem(vermicelliString, false)
        categoryTitlesArray.add(item)

        item = CategoryMenuItem(stirFryString, false)
        categoryTitlesArray.add(item)

        item = CategoryMenuItem(drinksString, false)
        categoryTitlesArray.add(item)

        categoryMenuAdapter.notifyDataSetChanged()
    }

    private fun createData(count: Int): ArrayList<MenuItem>
    {
        val array: ArrayList<MenuItem> = arrayListOf()

        for (itemCount in 0 until count)
        {
            val item = MenuItem(testItemTitle, testItemDescription, testItemCost)

            array.add(item)
        }
        return array
    }

    private val categoryMenuItemClickListener: CategoryMenuItemClickListener =
            object : CategoryMenuItemClickListener
            {
                override fun onCategoryMenuItemClicked(view: View, newPosition: Int)
                {
                    categoryTitlesArray[categoryMenuAdapter.getActivePosition()].setActiveState(false)

                    categoryTitlesArray[newPosition].setActiveState(true)

                    categoryMenuAdapter.setActivePosition(newPosition)

                    categoryMenuAdapter.notifyDataSetChanged()

                    categoryItemsArray = categoryArrays[newPosition]

                    categoryTitle = categoryTitlesArray[newPosition].getTitle()

                    menuAdapter = MenuItemAdapter(activity!!.applicationContext, categoryItemsArray, menuItemAddClickListener)

                    menu_items.adapter = menuAdapter

                    menuAdapter.notifyDataSetChanged()
                }

                override fun getActiveColour(): Int
                {
                    return ContextCompat.getColor(activity!!, R.color.secondary)
                }

                override fun getInactiveColour(): Int
                {
                    return ContextCompat.getColor(activity!!, R.color.white)
                }
            }

    private val menuItemAddClickListener: MenuItemAddClickListener =
            object : MenuItemAddClickListener
            {
                override fun onAddToOrderButtonClicked(view: View, position: Int)
                {
                    val title = categoryItemsArray[position].getTitle()

                    val description = categoryItemsArray[position].getDescription()

                    val cost = categoryItemsArray[position].getCost()

                    when (categoryTitlesArray[categoryMenuAdapter.getActivePosition()].getTitle())
                    {
                        soupsString, phoString ->
                        {
                            soupsItemAddDialog = SoupsItemAddDialog(activity!!, this, categoryTitlesArray[categoryMenuAdapter.getActivePosition()].getTitle())

                            soupsItemAddDialog.setup(title, description, cost)
                        }
                    }


                }

                override fun onAddToOrderDialogClicked(newlyAddedItems: Int, subTotal: Float)
                {
                    itemCount += newlyAddedItems
                    item_count.text = String.format(resources.getString(R.string.orders_item_count, itemCount.toString()))
                }
            }
}