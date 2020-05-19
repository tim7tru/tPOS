package com.timmytruong.timmypos.fragments

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
import com.timmytruong.timmypos.interfaces.CategoryTabSelectListener
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.ui.BasicItemAddDialog
import com.timmytruong.timmypos.utils.ui.SoupsPhoItemAddDialog
import com.timmytruong.timmypos.viewmodel.MenuViewModel
import kotlinx.android.synthetic.main.fragment_menu.*

class MenuFragment : Fragment(), CategoryTabSelectListener, MenuItemAddClickListener
{
    private lateinit var menuViewModel: MenuViewModel

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

                    menu_items.visibility = View.VISIBLE

                    menu_loading.visibility = View.GONE

                }
            }

    private val menuOptionsObserver: Observer<List<DialogOptionItem>> =
            Observer {
                it?.let {
                    menuViewModel.onMenuOptionsRetrieved(it)
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

    private fun setupViewModels()
    {
        menuViewModel = ViewModelProviders.of(this)[MenuViewModel::class.java]

        menuViewModel.menu.observe(this, menuObserver)

        menuViewModel.menuOptions.observe(this, menuOptionsObserver)

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

    private fun updateUI()
    {
        dataBinding.order = menuViewModel.getCurrentOrder()
    }

    private fun fetchData()
    {
        menuViewModel.fetch()
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
                        viewModel = menuViewModel
                )
            }
            AppConstants.SOUPS_DIALOG_TYPE ->
            {
                val dialog = SoupsPhoItemAddDialog(
                        context = view.context,
                        item = item,
                        menuViewModel = menuViewModel
                )

                dialog.showDialog()
            }
        }
    }
}