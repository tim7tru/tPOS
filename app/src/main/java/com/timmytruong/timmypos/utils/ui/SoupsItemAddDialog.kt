package com.timmytruong.timmypos.utils.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.adapters.DialogOptionItemsAdapter
import com.timmytruong.timmypos.dagger.component.DaggerAppComponent
import com.timmytruong.timmypos.interfaces.DialogItemClickListener
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.mapper.SoupsExtrasMapper
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.DataUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.utils.constants.DataConstants
import com.timmytruong.timmypos.viewmodel.SoupsExtrasViewModel
import kotlinx.android.synthetic.main.cancel_add_to_order_content.view.*
import kotlinx.android.synthetic.main.image_description_quantity_content.view.*
import kotlinx.android.synthetic.main.menu_item_add_dialog_title.view.*
import kotlinx.android.synthetic.main.fragment_soups_dialog.*
import javax.inject.Inject

class SoupsItemAddDialog(private val menuAddItemClickListener: MenuItemAddClickListener,
                         private val categoryTitle: String,
                         private val item: MenuItem): DialogFragment()
{
    @Inject lateinit var soupsExtrasMapper: SoupsExtrasMapper

    @Inject lateinit var soupsExtrasViewModel: SoupsExtrasViewModel

    private val sizesArray: ArrayList<DialogOptionItem> = arrayListOf()

    private val brothArray: ArrayList<DialogOptionItem> = arrayListOf()

    private lateinit var dialogOptionsBrothAdapter: DialogOptionItemsAdapter

    private lateinit var dialogOptionSizesAdapter: DialogOptionItemsAdapter

    private lateinit var dialogOptionExtrasAdapter: DialogOptionItemsAdapter

    private lateinit var unitCost: String

    private var newUnitCost: Float = 0f

    private var sizeCost: Float = 0f

    private var extrasCost: Float = 0f

    private var quantityNumber: Int = 1

    private var newCost: Float = 0f

    private val soupsExtrasObserver: Observer<List<DialogOptionItem>> =
        Observer {
            if (it != null && it.isNotEmpty())
            {
                for (index in it.indices)
                {
                    soupsExtrasViewModel.addSoupExtra(it[index])
                }
            }
            else if (it.isNullOrEmpty())
            {
                soupsExtrasViewModel.clearSoupExtraArray()

                soupsExtrasViewModel.setSoupExtra(DataUtils.getSoupsExtrasDataFromAssets(soupsExtrasMapper, activity!!))
            }

            dialogOptionExtrasAdapter.notifyDataSetChanged()
        }


    private val onCancelClickListener = View.OnClickListener {
        resetCheckedExtras()

        closeFragment()
    }

    private val onPlusClickListener = View.OnClickListener {
        when (quantityNumber)
        {
            1 ->
            {
                soups_image_desc_quantity.minus_quantity_shown.visibility = View.VISIBLE

                soups_image_desc_quantity.minus_quantity_hidden.visibility = View.INVISIBLE
            }
            98 ->
            {
                soups_image_desc_quantity.plus_quantity_shown.visibility = View.INVISIBLE

                soups_image_desc_quantity.plus_quantity_hidden.visibility = View.VISIBLE
            }
        }
        quantityNumber++

        updateAddText()

        updateQuantityText()

        updateCosts(newUnitCost)
    }

    private val onMinusClickListener = View.OnClickListener {
        when (quantityNumber)
        {
            2 ->
            {
                soups_image_desc_quantity.minus_quantity_shown.visibility = View.INVISIBLE

                soups_image_desc_quantity.minus_quantity_hidden.visibility = View.VISIBLE
            }
            99 ->
            {
                soups_image_desc_quantity.plus_quantity_shown.visibility = View.VISIBLE

                soups_image_desc_quantity.plus_quantity_hidden.visibility = View.INVISIBLE
            }
        }

        quantityNumber--

        updateAddText()

        updateQuantityText()

        updateCosts(newUnitCost)
    }

    private val onAddClickListener = View.OnClickListener {
        menuAddItemClickListener.onAddToOrderDialogClicked (
            DataUtils.buildOrderedItem (
                item = item,
                sizes = sizesArray,
                extras = soupsExtrasViewModel.getSoupExtras(),
                broths = brothArray,
                quantity = quantityNumber,
                unitCost = newUnitCost.toDouble()
            )
        )

        closeFragment()
    }

    private val dialogItemClickListener: DialogItemClickListener =
        object : DialogItemClickListener
        {
            override fun onItemClicked(position: Int, tag: String)
            {
                when (tag)
                {
                    AppConstants.SIZE_OPTION_TAG -> {
                        for (index in 0 until sizesArray.size)
                        {
                            if ((sizesArray[index].checkedStatus && index != position) || (sizesArray[index].checkedStatus && index == position))
                            {
                                sizesArray[index].checkedStatus = false
                            }
                            else if (!sizesArray[index].checkedStatus && index == position)
                            {
                                sizesArray[index].checkedStatus = true
                            }
                        }

                        dialogOptionSizesAdapter.notifyDataSetChanged()

                        sizeCost =
                            if (sizesArray[position].checkedStatus)
                            {
                                sizesArray[position].cost.toFloat()
                            }
                            else
                            {
                                0f
                            }

                    }
                    AppConstants.EXTRA_OPTION_TAG -> {
                        var unitValue: Float = soupsExtrasViewModel.getSoupExtras()[position].cost.toFloat()

                        when (soupsExtrasViewModel.getSoupExtras()[position].checkedStatus)
                        {
                            true -> unitValue = -unitValue
                        }

                        soupsExtrasViewModel.getSoupExtras()[position].checkedStatus = !soupsExtrasViewModel.getSoupExtras()[position].checkedStatus

                        dialogOptionExtrasAdapter.notifyDataSetChanged()

                        extrasCost += unitValue
                    }
                    AppConstants.BROTH_TAG -> {
                        for (index in 0 until brothArray.size)
                        {
                            if ((brothArray[index].checkedStatus && index != position) || (brothArray[index].checkedStatus && index == position))
                            {
                                brothArray[index].checkedStatus = false
                            }
                            else if (!brothArray[index].checkedStatus && index == position)
                            {
                                brothArray[index].checkedStatus = true
                            }

                            dialogOptionsBrothAdapter.notifyDataSetChanged()
                        }
                    }
                }

                newUnitCost = unitCost.toFloat() + sizeCost + extrasCost

                updateCosts(newUnitCost)
            }
        }

    override fun onAttach(context: Context?)
    {
        super.onAttach(context)

        DaggerAppComponent.create().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.fragment_soups_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        this.unitCost = item.cost

        newUnitCost = item.cost.toFloat()

        setupObservers()

        setInitialText()

        setBrothOptions()

        setAdapters()

        createSizeData()

        setOnClickListeners()

        updateCosts(unitCost.toFloat())

        updateAddText()

        updateQuantityText()
    }

    private fun closeFragment()
    {
        val fragment = fragmentManager?.findFragmentByTag(AppConstants.DIALOG_FRAGMENT_TAG)

        if (fragment != null)
        {
            val dialog = fragment as DialogFragment

            dialog.dismiss()
        }
    }

    private fun resetCheckedExtras()
    {
        soupsExtrasViewModel.getSoupExtras().forEach {
            it.resetChecked()
        }
    }

    private fun setupObservers()
    {
        soupsExtrasViewModel.getExtras()?.observe(this, soupsExtrasObserver)
    }

    private fun setBrothOptions()
    {
        if (!item.tags.isNullOrEmpty() && item.tags!!.contains(DataConstants.WITH_OR_WITHOUT_TAG))
        {
            broth_option.visibility = View.VISIBLE
            for (index in DataConstants.WITH_OR_WITHOUT_ARRAY.indices)
            {
                brothArray.add(DialogOptionItem(
                    name = DataConstants.WITH_OR_WITHOUT_ARRAY[index],
                    cost = "0",
                    optionTag = AppConstants.BROTH_TAG,
                    category = AppConstants.SOUPS_CATEGORY_TAG)
                )
            }
        }
        else
        {
            broth_option.visibility = View.GONE
        }
    }

    private fun setInitialText()
    {
        soups_image_desc_quantity.description_text.text = item.description

        soups_title.add_dialog_menu_item_title.text = CommonUtils.formatGeneralTitle(item.menuNumber, item.name)
    }

    private fun setAdapters()
    {
        dialogOptionsBrothAdapter = DialogOptionItemsAdapter(activity!!, brothArray , dialogItemClickListener)

        dialogOptionSizesAdapter = DialogOptionItemsAdapter(activity!!, sizesArray, dialogItemClickListener)

        dialogOptionExtrasAdapter = DialogOptionItemsAdapter(activity!!, soupsExtrasViewModel.getSoupExtras(), dialogItemClickListener)

        broth_option_body.layoutManager = LinearLayoutManager(activity)

        soups_sizes_body.layoutManager = LinearLayoutManager(activity)

        soups_extras_body.layoutManager = LinearLayoutManager(activity)

        broth_option_body.adapter = dialogOptionsBrothAdapter

        soups_sizes_body.adapter = dialogOptionSizesAdapter

        soups_extras_body.adapter = dialogOptionExtrasAdapter
    }

    private fun setOnClickListeners()
    {
        soups_image_desc_quantity.plus_quantity_shown.setOnClickListener(onPlusClickListener)

        soups_image_desc_quantity.minus_quantity_shown.setOnClickListener(onMinusClickListener)

        final_actions.add_dialog_positive_button.setOnClickListener(onAddClickListener)

        final_actions.add_dialog_negative_button.setOnClickListener(onCancelClickListener)
    }

    private fun createSizeData()
    {
        when (categoryTitle)
        {
            AppConstants.PHO_CATEGORY_TAG ->
            {
                var item = DialogOptionItem(checkedStatus = false, name = resources.getString(R.string.size_small), cost = "0", optionTag = AppConstants.SIZE_OPTION_TAG, category = AppConstants.PHO_CATEGORY_TAG)
                sizesArray.add(item)

                item = DialogOptionItem(checkedStatus = false, name = resources.getString(R.string.size_large_pho), cost = "1", optionTag = AppConstants.SIZE_OPTION_TAG, category = AppConstants.PHO_CATEGORY_TAG)
                sizesArray.add(item)

                item = DialogOptionItem(checkedStatus = false, name = resources.getString(R.string.size_xlarge_pho), cost = "4", optionTag = AppConstants.SIZE_OPTION_TAG, category = AppConstants.PHO_CATEGORY_TAG)
                sizesArray.add(item)
            }
            AppConstants.SOUPS_CATEGORY_TAG ->
            {
                var item = DialogOptionItem(checkedStatus = false, name = resources.getString(R.string.size_large_soups), cost = "0", optionTag = AppConstants.SIZE_OPTION_TAG, category = AppConstants.SOUPS_CATEGORY_TAG)
                sizesArray.add(item)

                item = DialogOptionItem(checkedStatus = false, name = resources.getString(R.string.size_xlarge_soups), cost = "4", optionTag = AppConstants.SIZE_OPTION_TAG, category = AppConstants.SOUPS_CATEGORY_TAG)
                sizesArray.add(item)
            }
        }

        dialogOptionSizesAdapter.notifyDataSetChanged()
    }

    private fun updateAddText()
    {
        final_actions.add_dialog_positive_button.text = String.format(resources.getString(R.string.orders_add_dialog), quantityNumber)
    }

    private fun updateQuantityText()
    {
        soups_image_desc_quantity.quantity_text.text = quantityNumber.toString()
    }

    private fun updateCosts(cost: Float)
    {
        soups_image_desc_quantity.price_per_item.text = String.format(resources.getString(R.string.orders_dialog_price_per_item), AppConstants.DECIMAL_FORMAT.format(cost))

        newCost = cost * quantityNumber

        soups_subtotal_cost.text = CommonUtils.formatGeneralCosts(AppConstants.DECIMAL_FORMAT.format(newCost))
    }


}