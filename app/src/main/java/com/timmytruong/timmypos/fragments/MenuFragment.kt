package com.timmytruong.timmypos.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.adapters.MenuItemAdapter
import com.timmytruong.timmypos.databinding.FragmentMenuBinding
import com.timmytruong.timmypos.interfaces.CategoryTabSelectListener
import com.timmytruong.timmypos.interfaces.DialogCallback
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.model.OrderedItem
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.ui.BasicItemAddDialog
import com.timmytruong.timmypos.viewmodel.MenuViewModel
import com.timmytruong.timmypos.viewmodel.SoupsExtrasViewModel
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment(), CategoryTabSelectListener, MenuItemAddClickListener, DialogCallback
{
    private lateinit var menuViewModel: MenuViewModel

    private lateinit var soupsExtrasViewModel: SoupsExtrasViewModel

    private lateinit var menuAdapter: MenuItemAdapter

    private lateinit var dataBinding: FragmentMenuBinding

    private var dialogHeight: Int = 0

    private var dialogWidth: Int = 0

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

        dataBinding.menuItems.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener
                {
                    override fun onGlobalLayout()
                    {
                        dataBinding.menuItems.viewTreeObserver.removeOnGlobalLayoutListener(this)

                        dialogHeight =
                                (menu_items.height * AppConstants.DIALOG_RESIZE_FACTOR).toInt()

                        dialogWidth = (menu_items.width * AppConstants.DIALOG_RESIZE_FACTOR).toInt()
                    }
                }
        )

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
        menu_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener
                                                 {
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
                                                             onCategorySelected(tab.position)
                                                         }
                                                     }
                                                 })
    }

    override fun onCategorySelected(newPosition: Int)
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

    override fun onAddToOrderButtonClicked(view: View, item: MenuItem)
    {
        when (item.dialog_type)
        {
            AppConstants.BASIC_DIALOG_TYPE ->
            {
                BasicItemAddDialog(
                        context = view.context,
                        item = item,
                        height = dialogHeight,
                        width = dialogWidth,
                        dialogCallback = this
                )
            }
            AppConstants.SOUPS_DIALOG_TYPE ->
            {

            }
        }
    }

    override fun onAddToOrderClicked(orderedItem: OrderedItem)
    {
        menuViewModel.addToOrder(orderedItem = orderedItem)
    }
}