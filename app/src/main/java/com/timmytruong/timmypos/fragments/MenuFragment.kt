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
import com.google.android.material.tabs.TabLayout
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.adapters.MenuItemAdapter
import com.timmytruong.timmypos.databinding.FragmentMenuBinding
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
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment(), MenuCategoryEventListener, MenuItemAddClickListener
{
    private lateinit var menuViewModel: MenuViewModel

    private lateinit var soupsExtrasViewModel: SoupsExtrasViewModel

    private lateinit var menuAdapter: MenuItemAdapter

    private lateinit var dataBinding: FragmentMenuBinding

    private val menuObserver: Observer<List<MenuItem>> =
            Observer {
                it?.let {
                    menuViewModel.onMenuRetrieved(it)

                    menuAdapter =
                            MenuItemAdapter(
                                    menuViewModel.getCategoryItems(menuViewModel.getCurrentCategoryTitle()),
                                    this
                            )

                    menu_items.layoutManager = LinearLayoutManager(context)

                    menu_items.adapter = menuAdapter

                    menuAdapter.notifyDataSetChanged()

//                    categoryMenuAdapter.notifyDataSetChanged()

                    menu_items.visibility = View.VISIBLE

                    menu_loading.visibility = View.GONE

                }
            }

    private val soupsExtrasObserver: Observer<List<DialogOptionItem>> =
            Observer {
                it?.let {
                    soupsExtrasViewModel.onSoupsExtrasRetrieved(it)
                }
            }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?
    {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        setupViewModels()

        setupTabs()

        updateUI()

        fetchData()
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

    private fun setupTabs()
    {
        menu_tab_layout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?)
            {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?)
            {
            }

            override fun onTabSelected(tab: TabLayout.Tab?)
            {
                if (tab != null)
                {
                    onCategoryMenuItemClicked(tab.position)
                }
            }
        })
    }

    override fun onCategoryMenuItemClicked(newPosition: Int)
    {
        menuViewModel.onCategoryMenuItemClicked(newPosition = newPosition)

        menuAdapter =
                MenuItemAdapter(
                        menuViewModel.getCategoryItems(menuViewModel.getCurrentCategoryTitle()),
                        this
                )

        menu_items.adapter = menuAdapter

        menuAdapter.notifyDataSetChanged()
    }

    override fun onAddToOrderButtonClicked(view: View, position: Int)
    {
        when (menuViewModel.getCategoryItems(menuViewModel.getCurrentCategoryTitle())[position].dialog_type)
        {
            AppConstants.BASIC_DIALOG_TYPE ->
            {
                val basicItemAddDialog = BasicItemAddDialog(
                        context = view.context,
                        menuItemAddClickListener = this,
                        item = menuViewModel.getCategoryItems(menuViewModel.getCurrentCategoryTitle())[position]
                )

                basicItemAddDialog.setup()
            }
            AppConstants.SOUPS_DIALOG_TYPE ->
            {
                val soupsItemAddDialog = SoupsItemAddDialog(
                        mContext = view.context,
                        menuAddItemClickListener = this,
                        categoryTitle = menuViewModel.getCurrentCategoryTitle(),
                        item = menuViewModel.getCategoryItems(menuViewModel.getCurrentCategoryTitle())[position],
                        soupsExtraArray = soupsExtrasViewModel.getSoupsExtras()
                )

                soupsItemAddDialog.show()
            }
        }    }

    override fun onAddToOrderDialogClicked(orderedItem: OrderedItem)
    {
        updateUI()

        menuViewModel.addToOrder(orderedItem = orderedItem)
    }
}