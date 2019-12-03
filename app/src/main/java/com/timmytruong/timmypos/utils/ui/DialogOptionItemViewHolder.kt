package com.timmytruong.timmypos.utils.ui

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.DialogItemClickListener
import com.timmytruong.timmypos.models.DialogOptionItem

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
        dialogTitle.text = item.optionTitle
    }
}