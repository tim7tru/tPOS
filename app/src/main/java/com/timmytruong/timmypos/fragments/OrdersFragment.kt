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
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.utils.CommonUtils
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

    private lateinit var categoryMenuAdapter: CategoryMenuAdapter

    private lateinit var menuAdapter: MenuItemAdapter

    private val menuObserver: Observer<List<MenuItem>> =
            Observer {
                it?.let {
                    menuViewModel.onMenuRetrieved(it)

                    menuAdapter =
                            MenuItemAdapter(
                                    menuViewModel.getCategoryItems(menuViewModel.getCurrentCategoryTitle()),
                                    menuItemAddClickListener
                            )

                    menu_items.layoutManager = LinearLayoutManager(context)

                    menu_items.adapter = menuAdapter

                    menuAdapter.notifyDataSetChanged()
                }
            }

    private val soupsExtrasObserver: Observer<List<DialogOptionItem>> =
            Observer {
                if (it != null && it.isNotEmpty())
                {
                    soupsExtrasViewModel.onSoupsExtrasRetrieved(it)
                }
                else if (it.isNullOrEmpty())
                {
                    val assets = context!!.assets

                    soupsExtrasViewModel.getExtras(assets = assets)

                    return@Observer
                }
            }

    private val categoryMenuItemClickListener: CategoryMenuItemClickListener =
            object : CategoryMenuItemClickListener
            {
                override fun onCategoryMenuItemClicked(view: View, newPosition: Int)
                {
                    menuViewModel.onCategoryMenuItemClicked(
                            oldPosition = categoryMenuAdapter.getActivePosition(),
                            newPosition = newPosition
                    )

                    categoryMenuAdapter.setActivePosition(newPosition)

                    categoryMenuAdapter.notifyDataSetChanged()

                    menuAdapter =
                            MenuItemAdapter(
                                    menuViewModel.getCategoryItems(menuViewModel.getCurrentCategoryTitle()),
                                    menuItemAddClickListener
                            )

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
                    when (menuViewModel.getCategoryItems(menuViewModel.getCurrentCategoryTitle())[position].dialog_type)
                    {
                        AppConstants.BASIC_DIALOG_TYPE ->
                        {
                            val basicItemAddDialog = BasicItemAddDialog(
                                    context = context!!,
                                    menuItemAddClickListener = this,
                                    item = menuViewModel.getCategoryItems(menuViewModel.getCurrentCategoryTitle())[position]
                            )

                            basicItemAddDialog.setup()
                        }
                        AppConstants.SOUPS_DIALOG_TYPE ->
                        {
                            val soupsItemAddDialog = SoupsItemAddDialog(
                                    context = context!!,
                                    menuAddItemClickListener = this,
                                    categoryTitle = menuViewModel.getCurrentCategoryTitle(),
                                    item = menuViewModel.getCategoryItems(menuViewModel.getCurrentCategoryTitle())[position],
                                    soupsExtraArray = soupsExtrasViewModel.getSoupsExtras()
                            )

                            soupsItemAddDialog.setup()
                        }
                    }
                }

                override fun onAddToOrderDialogClicked(orderedItem: OrderedItem)
                {
                    updateItemCountView()

                    menuViewModel.addToOrder(orderedItem = orderedItem)
                }
            }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?
    {
        return inflater.inflate(R.layout.fragment_orders, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupViewModels()

        menuViewModel.setupData()

        updateItemCountView()

        setupAdapters()

        menuViewModel.fetch()
    }


    private fun setupViewModels()
    {
        soupsExtrasViewModel = ViewModelProviders.of(this)[SoupsExtrasViewModel::class.java]

        menuViewModel = ViewModelProviders.of(this)[MenuViewModel::class.java]

        soupsExtrasViewModel.getExtras().observe(this, soupsExtrasObserver)

        menuViewModel.menu.observe(this, menuObserver)
    }

    private fun setupAdapters()
    {
        categoryMenuAdapter = CategoryMenuAdapter(
                menuViewModel.getCategoryTitles(),
                categoryMenuItemClickListener
        )

        category_menu.layoutManager = LinearLayoutManager(context)

        category_menu.adapter = categoryMenuAdapter

        categoryMenuAdapter.notifyDataSetChanged()
    }

    private fun updateItemCountView()
    {
        item_count.text = CommonUtils.formatItemCount(menuViewModel.getItemCount().toString())
    }
}