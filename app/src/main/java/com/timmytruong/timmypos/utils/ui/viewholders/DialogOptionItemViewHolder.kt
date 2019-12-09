package com.timmytruong.timmypos.utils.ui.viewholders

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.internal.service.Common
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.DialogItemClickListener
import com.timmytruong.timmypos.models.DialogOptionItem
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
            dialogItemClickListener.onItemClicked(this.layoutPosition, item.tag)
        }

        checkbox.isChecked = item.checkedStatus

        if (item.cost.toInt() == 0)
        {
            dialogTitle.text = CommonUtils.formatGeneralString(item.name)
        }
        else
        {
            dialogTitle.text = CommonUtils.formatDialogExtrasTitle(item.name, AppConstants.DECIMAL_FORMAT.format(item.cost.toDouble()).toString())
        }
    }
}