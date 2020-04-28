package com.timmytruong.timmypos.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.adapters.CategoryMenuAdapter
import com.timmytruong.timmypos.adapters.MenuItemAdapter
import com.timmytruong.timmypos.interfaces.CategoryMenuItemClickListener
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.model.CategoryMenuItem
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.provider.MenuProvider
import com.timmytruong.timmypos.provider.SoupsExtrasProvider
import com.timmytruong.timmypos.utils.DataUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.ui.BasicItemAddDialog
import com.timmytruong.timmypos.utils.ui.SoupsItemAddDialog
import com.timmytruong.timmypos.viewmodel.MenuViewModel
import com.timmytruong.timmypos.viewmodel.SoupsExtrasViewModel
import kotlinx.android.synthetic.main.fragment_orders.*

class OrdersFragment : Fragment()
{
    private lateinit var menuViewModel: MenuViewModel

    private lateinit var soupsExtrasViewModel: SoupsExtrasViewModel

    private lateinit var soupsExtrasProvider: SoupsExtrasProvider

    private lateinit var testItemTitle: String

    private lateinit var testItemDescription: String

    private lateinit var testItemCost: String

    private lateinit var itemCountTemplate: String

    private lateinit var menuProvider: MenuProvider

    private lateinit var categoryMenuAdapter: CategoryMenuAdapter

    private lateinit var menuAdapter: MenuItemAdapter

    private var itemCount: Int = 0

    private var categoryTitle: String = ""

    private val menuObserver: Observer<List<ArrayList<MenuItem>>> =
        Observer {
            if (it != null  && it.isNotEmpty())
            {
                menuProvider.categoryArrays.clear()

                for (index in it.indices)
                {
                    if (it[index].isNotEmpty())
                    {
                        menuProvider.categoryArrays.add(ArrayList(it[index]))
                    }
                }

                /**
                 * Dummy Data
                 */
                menuProvider.categoryArrays.add(createData(9))

                menuProvider.categoryArrays.add(createData(15))

                menuProvider.categoryArrays.add(createData(14))

                menuProvider.categoryArrays.add(createData(3))

                menuProvider.categoryArrays.add(createData(9))

                menuProvider.categoryArrays.add(createData(9))

                menuProvider.categoryArrays.add(createData(22))
            }
            else if (it.isNullOrEmpty())
            {
                val assets = context!!.assets

                menuViewModel.getMenu(assets)

                return@Observer
            }

            menuProvider.categoryItemsArray.addAll(menuProvider.categoryArrays[0])

            menuAdapter = MenuItemAdapter(menuProvider.categoryItemsArray, menuItemAddClickListener)

            menu_items.layoutManager = LinearLayoutManager(context)

            menu_items.adapter = menuAdapter

            menuAdapter.notifyDataSetChanged()
        }

    private val soupsExtrasObserver: Observer<List<DialogOptionItem>> =
        Observer {
            if (it != null && it.isNotEmpty())
            {
                soupsExtrasProvider.soupsExtrasArray.clear()

                for (index in it.indices)
                {
                    soupsExtrasProvider.soupsExtrasArray.add(it[index])
                }
            }
            else if (it.isNullOrEmpty())
            {
                val assets = context!!.assets

                soupsExtrasViewModel.getExtras(assets = assets)
            }
        }

    private val categoryMenuItemClickListener: CategoryMenuItemClickListener =
        object : CategoryMenuItemClickListener
        {
            override fun onCategoryMenuItemClicked(view: View, newPosition: Int)
            {
                menuProvider.categoryTitlesArray[categoryMenuAdapter.getActivePosition()].activeState = false

                menuProvider.categoryTitlesArray[newPosition].activeState = true

                categoryMenuAdapter.setActivePosition(newPosition)

                categoryMenuAdapter.notifyDataSetChanged()

                menuProvider.categoryItemsArray = menuProvider.categoryArrays[newPosition]

                categoryTitle = menuProvider.categoryTitlesArray[newPosition].title

                menuAdapter = MenuItemAdapter(menuProvider.categoryItemsArray, menuItemAddClickListener)

                menu_items.adapter = menuAdapter

                menuAdapter.notifyDataSetChanged()
            }

            override fun getActiveColour(): Int
            {
                return ContextCompat.getColor(context!!, R.color.secondary)
            }

            override fun getInactiveColour(): Int
            {
                return ContextCompat.getColor(context!!, R.color.white)
            }
        }

    private val menuItemAddClickListener: MenuItemAddClickListener =
        object : MenuItemAddClickListener
        {
            @SuppressLint("DefaultLocale")
            override fun onAddToOrderButtonClicked(view: View, position: Int)
            {
                when (menuProvider.categoryItemsArray[position].dialogType)
                {
                    AppConstants.BASIC_DIALOG_TYPE ->
                    {
                        val basicItemAddDialog = BasicItemAddDialog (
                            context = context!!,
                            menuItemAddClickListener = this,
                            item = menuProvider.categoryItemsArray[position]
                        )

                        basicItemAddDialog.setup()
                    }
                    AppConstants.SOUPS_DIALOG_TYPE ->
                    {
                        val soupsItemAddDialog = SoupsItemAddDialog (
                            context = context!!,
                            menuAddItemClickListener = this,
                            categoryTitle = categoryTitle,
                            item = menuProvider.categoryItemsArray[position],
                            soupsExtraArray = soupsExtrasProvider.soupsExtrasArray
                        )

                        soupsItemAddDialog.setup()
                    }
                }
            }

            override fun onAddToOrderDialogClicked(orderedItem: OrderedItem)
            {
                itemCount += orderedItem.quantity

                updateItemCountView()

                menuProvider.orderedItemsArray.add(orderedItem)
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        menuProvider = MenuProvider()

        soupsExtrasProvider = SoupsExtrasProvider()

        setupViewModels()

        loadStringsFromResources()

        updateItemCountView()

        categoryMenuAdapter = CategoryMenuAdapter(menuProvider.categoryTitlesArray, categoryMenuItemClickListener)

        category_menu.layoutManager = LinearLayoutManager(context)

        category_menu.adapter = categoryMenuAdapter

        createCategoryData()
    }


    private fun setupViewModels()
    {
        soupsExtrasViewModel = ViewModelProviders.of(this)[SoupsExtrasViewModel::class.java]

        menuViewModel = ViewModelProviders.of(this)[MenuViewModel::class.java]

        soupsExtrasViewModel.getExtras().observe(this, soupsExtrasObserver)

        menuViewModel.getMenu().observe(this, menuObserver)
    }

    private fun loadStringsFromResources()
    {
        itemCountTemplate = resources.getString(R.string.orders_item_count)

        testItemTitle = resources.getString(R.string.test_item_title)

        testItemDescription = resources.getString(R.string.test_item_description)

        testItemCost = resources.getString(R.string.test_item_cost)
    }

    private fun updateItemCountView()
    {
        item_count.text = String.format(itemCountTemplate, itemCount.toString())
    }

    private fun createCategoryData()
    {
        var activeState = true

        for (index in AppConstants.MENU_CATEGORY_ARRAY)
        {
            val item = CategoryMenuItem(index, activeState)

            activeState = false

            menuProvider.categoryTitlesArray.add(item)
        }

        categoryTitle = menuProvider.categoryTitlesArray[0].title

        categoryMenuAdapter.notifyDataSetChanged()
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
}