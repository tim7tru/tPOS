package com.timmytruong.timmypos.utils.ui.viewholders

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.DialogItemClickListener
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.utils.CommonUtils
import com.timmytruong.timmypos.utils.constants.AppConstants

class DialogOptionItemViewHolder(itemView: View,
                                 private val dialogItemClickListener: DialogItemClickListener): RecyclerView.ViewHolder(itemView)
{
    private val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)
    private val dialogTitle: TextView = itemView.findViewById(R.id.dialog_title)

    fun setDetails(item: DialogOptionItem)
    {
        itemView.setOnClickListener {
            dialogItemClickListener.onItemClicked(this.layoutPosition, item.optionTag)
        }

        checkbox.isChecked = item.checkedStatus

        if (item.optionTag == AppConstants.EXTRA_OPTION_TAG)
        {
            if (item.cost.toInt() == 0)
            {
                dialogTitle.text = CommonUtils.formatGeneralString(item.name)
            }
            else
            {
                dialogTitle.text = CommonUtils.formatDialogExtrasTitle(item.name, AppConstants.DECIMAL_FORMAT.format(item.cost.toDouble()).toString())
            }
        }
        else if (item.optionTag == AppConstants.SIZE_OPTION_TAG)
        {
            if (item.cost.toInt() == 0)
            {
                dialogTitle.text = CommonUtils.formatGeneralString(item.name)
            }
            else
            {
                dialogTitle.text = CommonUtils.formatDialogSizesTitle(item.name, AppConstants.DECIMAL_FORMAT.format(item.cost.toDouble()).toString())
            }
        }
        else if (item.optionTag == AppConstants.BROTH_TAG)
        {
            dialogTitle.text = CommonUtils.formatGeneralString(item.name)
        }

    }
}