package com.timmytruong.timmypos.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.adapters.CategoryMenuAdapter
import com.timmytruong.timmypos.adapters.MenuItemAdapter
import com.timmytruong.timmypos.databinding.FragmentOrdersBinding
import com.timmytruong.timmypos.interfaces.MenuCategoryEventListener
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.ui.BasicItemAddDialog
import com.timmytruong.timmypos.utils.ui.SoupsItemAddDialog
import com.timmytruong.timmypos.viewmodel.MenuViewModel
import com.timmytruong.timmypos.viewmodel.SoupsExtrasViewModel
import kotlinx.android.synthetic.main.fragment_orders.*

class OrdersFragment : Fragment(), MenuCategoryEventListener
{
    private lateinit var menuViewModel: MenuViewModel

    private lateinit var soupsExtrasViewModel: SoupsExtrasViewModel

    private lateinit var categoryMenuAdapter: CategoryMenuAdapter

    private lateinit var menuAdapter: MenuItemAdapter

    private lateinit var dataBinding: FragmentOrdersBinding

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

//                    categoryMenuAdapter.notifyDataSetChanged()

                    menu_items.visibility = View.VISIBLE

                    loading_menu.visibility = View.GONE

                }
            }

    private val soupsExtrasObserver: Observer<List<DialogOptionItem>> =
            Observer {
                it?.let {
                    soupsExtrasViewModel.onSoupsExtrasRetrieved(it)
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
                    updateUI()

                    menuViewModel.addToOrder(orderedItem = orderedItem)
                }
            }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?
    {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_orders, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupViewModels()

        setupAdapters()

        setRefreshListener()

        updateUI()

        fetchData()
    }

    private fun setRefreshListener()
    {
        refresh_menu_layout.setOnRefreshListener {

            menu_items.visibility = View.GONE

            loading_menu.visibility = View.VISIBLE

            menuViewModel.refreshBypassCache()

            refresh_menu_layout.isRefreshing = false
        }
    }

    private fun fetchData()
    {
        menuViewModel.fetch()

        soupsExtrasViewModel.fetch()
    }

    private fun updateUI()
    {
        dataBinding.order = menuViewModel.getCurrentOrder()
    }

    private fun setupViewModels()
    {
        soupsExtrasViewModel = ViewModelProviders.of(this)[SoupsExtrasViewModel::class.java]

        menuViewModel = ViewModelProviders.of(this)[MenuViewModel::class.java]

        soupsExtrasViewModel.soupsExtras.observe(this, soupsExtrasObserver)

        menuViewModel.menu.observe(this, menuObserver)

        menuViewModel.setupData()
    }

    private fun setupAdapters()
    {
        categoryMenuAdapter = CategoryMenuAdapter(
                menuViewModel.getCategoryTitles(),
                this
        )

        category_menu.layoutManager = LinearLayoutManager(context)

        categoryMenuAdapter.setHasStableIds(true)

        category_menu.adapter = categoryMenuAdapter

        (category_menu.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        categoryMenuAdapter.notifyDataSetChanged()
    }


    override fun onCategoryMenuItemClicked(view: View, oldPosition: Int, newPosition: Int)
    {
        menuViewModel.onCategoryMenuItemClicked(
                oldPosition = oldPosition,
                newPosition = newPosition
        )

        categoryMenuAdapter.activePosition = newPosition

        categoryMenuAdapter.notifyDataSetChanged()

        menuAdapter =
                MenuItemAdapter(
                        menuViewModel.getCategoryItems(menuViewModel.getCurrentCategoryTitle()),
                        menuItemAddClickListener
                )

        menu_items.adapter = menuAdapter

        menuAdapter.notifyDataSetChanged()
    }
}