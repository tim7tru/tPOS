package com.timmytruong.timmypos.utils.ui

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.databinding.AlertOptionItemBinding
import com.timmytruong.timmypos.databinding.AlertSoupBinding
import com.timmytruong.timmypos.databinding.AlertTitleBinding
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.AppConstants
import com.timmytruong.timmypos.viewmodel.MenuViewModel

class SoupsPhoItemAddDialog(
        private val context: Context,
        private val menuViewModel: MenuViewModel,
        item: MenuItem
) : BaseAddDialog<AlertTitleBinding, AlertSoupBinding>(item = item)
{
    private var sizes: ArrayList<DialogOptionItem> = arrayListOf()

    private var broths: ArrayList<DialogOptionItem> = arrayListOf()

    private var extras: ArrayList<DialogOptionItem> = arrayListOf()

    private lateinit var inflater: LayoutInflater

    private val withOrWithoutSoup: ObservableBoolean = ObservableBoolean(false)

    private val soupsId = CommonUtils.findCategoryId(AppConstants.SOUPS_CATEGORY_TAG)

    private val phoId = CommonUtils.findCategoryId(AppConstants.PHO_CATEGORY_TAG)

    fun showDialog()
    {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        bindView(
                inflater = inflater,
                title = R.layout.alert_title,
                body = R.layout.alert_soup,
                viewModel = menuViewModel
        )

        setupDialog(context = context)

        setupChips()

        executeDataBinding()
    }

    override fun executeDataBinding()
    {
        titleDataBinding.item = item

        bodyDataBinding.item = item

        bodyDataBinding.alertPicQuantitySoup.item = item

        bodyDataBinding.alertPicQuantitySoup.quantity = quantityNumber

        bodyDataBinding.alertPicQuantitySoup.listener = this

        bodyDataBinding.finalActionsSoup.listener = this

        bodyDataBinding.finalActionsSoup.cost = orderCost

        bodyDataBinding.finalActionsSoup.quantity = quantityNumber

        bodyDataBinding.broth = withOrWithoutSoup
    }

    private fun setupChips()
    {
        when (item.category_id)
        {
            phoId   -> setupPho()
            soupsId -> setupSoups()
        }

        for (index in sizes.indices)
        {
            bodyDataBinding.sizeChipGroup.addView(createChip(inflater, sizes[index], index).root)
        }

        for (index in extras.indices)
        {
            bodyDataBinding.extraChipGroup.addView(createChip(inflater, extras[index], index).root)
        }

        if (broths.isNotEmpty())
        {
            for (index in broths.indices)
            {
                bodyDataBinding.brothChipGroup.addView(createChip(inflater, broths[index], index).root)
            }
        }

        setupCheckedChipListeners()
    }

    private fun createChip(
            inflater: LayoutInflater,
            extra: DialogOptionItem,
            id: Int
    ): AlertOptionItemBinding
    {
        val chip: AlertOptionItemBinding =
                DataBindingUtil.inflate(inflater, R.layout.alert_option_item, null, false)

        chip.extra = extra

        chip.checked = extra.checkedStatus

        chip.root.id = id

        return chip
    }

    private fun setupPho()
    {
        sizes = menuViewModel.getMenuOptions(phoId, AppConstants.SIZE_OPTION_TAG)

        extras = menuViewModel.getMenuOptions(soupsId, AppConstants.EXTRA_OPTION_TAG)
    }

    private fun setupSoups()
    {
        sizes = menuViewModel.getMenuOptions(soupsId, AppConstants.SIZE_OPTION_TAG)

        extras = menuViewModel.getMenuOptions(soupsId, AppConstants.EXTRA_OPTION_TAG)

        when (item.tags.contains(AppConstants.WITH_OR_WITHOUT_OPTION_TAG))
        {
            true ->
            {
                broths = menuViewModel.getMenuOptions(
                        soupsId,
                        AppConstants.WITH_OR_WITHOUT_OPTION_TAG
                )

                withOrWithoutSoup.set(true)
            }
        }
    }

    private fun setupCheckedChipListeners()
    {
        bodyDataBinding.sizeChipGroup.setOnCheckedChangeListener { chipGroup: ChipGroup, id: Int ->
            for (child in 0 until chipGroup.childCount)
            {
                val childId = (chipGroup.getChildAt(child) as Chip).id

                sizes[child].checkedStatus.set(childId == id)
            }
        }

        bodyDataBinding.brothChipGroup.setOnCheckedChangeListener { chipGroup: ChipGroup, id: Int ->
            for (child in 0 until chipGroup.childCount)
            {
                val childId = (chipGroup.getChildAt(child) as Chip).id

                broths[child].checkedStatus.set(childId == id)
            }
        }
    }
}