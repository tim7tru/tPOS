package com.timmytruong.timmypos.utils.ui

import android.content.Context
import android.view.LayoutInflater
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.databinding.AlertBasicBinding
import com.timmytruong.timmypos.databinding.AlertTitleBinding
import com.timmytruong.timmypos.model.MenuItem
import com.timmytruong.timmypos.viewmodel.MenuViewModel

open class BasicItemAddDialog(
        val context: Context,
        viewModel: MenuViewModel,
        item: MenuItem
) : BaseAddDialog<AlertTitleBinding, AlertBasicBinding>(item = item)
{
    init
    {
        bindView(
                inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                title = R.layout.alert_title,
                body = R.layout.alert_basic,
                viewModel = viewModel
        )

        setupDialog(context = context)

        executeDataBinding()
    }

    final override fun executeDataBinding()
    {
        titleDataBinding.item = item

        bodyDataBinding.item = item

        bodyDataBinding.listener = this

        bodyDataBinding.alertPicQuantity.item = item

        bodyDataBinding.alertPicQuantity.quantity = quantityNumber

        bodyDataBinding.alertPicQuantity.listener = this

        bodyDataBinding.finalActions.listener = this

        bodyDataBinding.finalActions.cost = orderCost

        bodyDataBinding.finalActions.quantity = quantityNumber
    }
}