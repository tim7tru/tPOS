package com.timmytruong.timmypos.utils.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.CategoryMenuItemClickListener
import com.timmytruong.timmypos.models.CategoryMenuItem

class CategoryMenuViewHolder(itemView: View, private val onClickListener: CategoryMenuItemClickListener) : RecyclerView.ViewHolder(itemView)
{
    private val titleText: TextView = itemView.findViewById(R.id.category_menu_title)

    private val onClick = View.OnClickListener {
        onClickListener.onCategoryMenuItemClicked(it!!, this.layoutPosition)
    }

    init
    {
        itemView.setOnClickListener(onClick)
    }

    fun setDetails(item: CategoryMenuItem)
    {
        if (item.getActiveState())
        {
            titleText.setTextColor(onClickListener.getActiveColour())
        }
        else
        {
            titleText.setTextColor(onClickListener.getInactiveColour())
        }

        titleText.text = item.getTitle()
    }
}