package com.timmytruong.timmypos.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.activity.ViewOrderActivity
import com.timmytruong.timmypos.adapters.CategoryMenuAdapter
import com.timmytruong.timmypos.adapters.MenuItemAdapter
import com.timmytruong.timmypos.dagger.component.DaggerAppComponent
import com.timmytruong.timmypos.interfaces.CategoryMenuItemClickListener
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.mapper.MenuMapper
import com.timmytruong.timmypos.model.CategoryMenuItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.utils.DataUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.ui.BasicItemAddDialog
import com.timmytruong.timmypos.utils.ui.SoupsItemAddDialog
import com.timmytruong.timmypos.viewmodel.MenuViewModel
import kotlinx.android.synthetic.main.fragment_orders.*
import javax.inject.Inject

class OrdersFragment : Fragment()
{
    @Inject lateinit var menuViewModel: MenuViewModel

    @Inject lateinit var menuMapper: MenuMapper

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

    private lateinit var itemCountTemplate: String

    private lateinit var buttonAnimation: Animation

    private val orderedItemsArray: ArrayList<OrderedItem> = arrayListOf()

    private lateinit var categoryMenuAdapter: CategoryMenuAdapter

    private lateinit var menuAdapter: MenuItemAdapter

    private var itemCount: Int = 0

    private var categoryTitle: String = ""

    private val menuObserver: Observer<List<ArrayList<MenuItem>>> =
        Observer {
            if (it != null  && it.isNotEmpty())
            {
                for (index in it.indices)
                {
                    if (it[index].isNotEmpty())
                    {
                        menuViewModel.addMenuItems(it[index])
                    }
                }

                menuViewModel.addMenuItems(createData(9))

                menuViewModel.addMenuItems(createData(15))

                menuViewModel.addMenuItems(createData(14))

                menuViewModel.addMenuItems(createData(3))

                menuViewModel.addMenuItems(createData(9))

                menuViewModel.addMenuItems(createData(9))

                menuViewModel.addMenuItems(createData(22))
            }
            else if (it.isNullOrEmpty())
            {
                menuViewModel.clearMenuItems()

                menuViewModel.setMenuItems(DataUtils.getMenuDataFromAssets(menuMapper, activity!!))
            }

            menuViewModel.setCategoryItems(menuViewModel.getMenuItems()[0])

            menuAdapter = MenuItemAdapter(activity!!, menuViewModel.getCategoryItems(), menuItemAddClickListener)

            menu_items.layoutManager = LinearLayoutManager(activity!!)

            menu_items.adapter = menuAdapter

            menuAdapter.notifyDataSetChanged()
        }

    private val categoryMenuItemClickListener: CategoryMenuItemClickListener =
        object : CategoryMenuItemClickListener
        {
            override fun onCategoryMenuItemClicked(view: View, newPosition: Int)
            {
                menuViewModel.getCategoryTitles()[categoryMenuAdapter.getActivePosition()].activeState = false

                menuViewModel.getCategoryTitles()[newPosition].activeState = true

                categoryMenuAdapter.setActivePosition(newPosition)

                categoryMenuAdapter.notifyDataSetChanged()

                menuViewModel.setCategoryItems(menuViewModel.getMenuItems()[newPosition])

                categoryTitle = menuViewModel.getCategoryTitles()[newPosition].title

                menuAdapter = MenuItemAdapter(activity!!.applicationContext, menuViewModel.getCategoryItems(), menuItemAddClickListener)

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
            @SuppressLint("DefaultLocale")
            override fun onAddToOrderButtonClicked(view: View, position: Int)
            {
                var newFragment: Fragment? = null

                when (menuViewModel.getCategoryItems()[position].dialogType)
                {
                    AppConstants.BASIC_DIALOG_TYPE ->
                    {
                        newFragment = BasicItemAddDialog (
                            menuItemAddClickListener = this,
                            item = menuViewModel.getCategoryItems()[position]
                        )

                    }
                    AppConstants.SOUPS_DIALOG_TYPE ->
                    {
                        newFragment = SoupsItemAddDialog (
                            menuAddItemClickListener = this,
                            categoryTitle = categoryTitle,
                            item = menuViewModel.getCategoryItems()[position]
                        )
                    }
                }

                if (newFragment != null)
                {
                    fragmentManager?.beginTransaction()?.add(newFragment, AppConstants.DIALOG_FRAGMENT_TAG)?.commit()
                }
            }

            override fun onAddToOrderDialogClicked(orderedItem: OrderedItem)
            {
                itemCount += orderedItem.quantity

                updateItemCountView()

                orderedItemsArray.add(orderedItem)
            }
        }

    private val viewOrderClickListener = View.OnClickListener {
        buttonAnimation.setAnimationListener(AppConstants.interactableAnimListener)

        if (AppConstants.interactable)
        {


            val intent = Intent(activity!!, ViewOrderActivity::class.java)

            intent.putExtra(AppConstants.ORDERED_ITEMS_ARRAY_LIST_INTENT_KEY, orderedItemsArray)

            startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        DaggerAppComponent.create().inject(this)

        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupViewModels()

        loadResources()

        updateItemCountView()

        setOnClickListeners()

        categoryMenuAdapter = CategoryMenuAdapter(activity!!.applicationContext, menuViewModel.getCategoryTitles(), categoryMenuItemClickListener)

        category_menu.layoutManager = LinearLayoutManager(activity!!)

        category_menu.adapter = categoryMenuAdapter

        categoryTitle = appetizersString

        createCategoryData()
    }

    private fun setOnClickListeners()
    {
        view_order.setOnClickListener {
            buttonAnimation.setAnimationListener(AppConstants.interactableAnimListener)

            if (AppConstants.interactable) {
                view_order.startAnimation(buttonAnimation)

                val intent = Intent(activity!!, ViewOrderActivity::class.java)

                intent.putExtra(AppConstants.ORDERED_ITEMS_ARRAY_LIST_INTENT_KEY, orderedItemsArray)

                startActivity(intent)
            }
        }
    }

    private fun setupViewModels()
    {
        menuViewModel.getMenu()?.observe(this, menuObserver)
    }

    private fun loadResources()
    {
        appetizersString = resources.getString(R.string.orders_appetizers)

        soupsString = resources.getString(R.string.orders_soups)

        phoString = resources.getString(R.string.orders_pho)

        riceString = resources.getString(R.string.orders_rice)

        friedRiceString = resources.getString(R.string.orders_fried_rice)

        vermicelliString = resources.getString(R.string.orders_vermicelli)

        stirFryString = resources.getString(R.string.orders_stir_fry)

        drinksString = resources.getString(R.string.orders_drinks)

        itemCountTemplate = resources.getString(R.string.orders_item_count)

        testItemTitle = resources.getString(R.string.test_item_title)

        testItemDescription = resources.getString(R.string.test_item_description)

        testItemCost = resources.getString(R.string.test_item_cost)

        buttonAnimation = AnimationUtils.loadAnimation(activity!!, R.anim.button_click_anim)
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

            menuViewModel.addCategoryTitle(item)
        }

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