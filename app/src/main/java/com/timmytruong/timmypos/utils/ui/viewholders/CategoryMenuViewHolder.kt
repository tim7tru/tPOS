package com.timmytruong.timmypos.utils.ui.viewholders

import android.view.View
import android.view.animation.Animation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.CategoryMenuItemClickListener
import com.timmytruong.timmypos.model.CategoryMenuItem

class CategoryMenuViewHolder(itemView: View,
                             private val onClickListener: CategoryMenuItemClickListener,
                             private val animation: Animation) : RecyclerView.ViewHolder(itemView)
{
    private val titleText: TextView = itemView.findViewById(R.id.category_menu_title)

    private val onClick = View.OnClickListener {
        titleText.startAnimation(animation)
        onClickListener.onCategoryMenuItemClicked(it!!, this.layoutPosition)
    }

    init
    {
        itemView.setOnClickListener(onClick)
    }

    fun setDetails(item: CategoryMenuItem)
    {
        if (item.activeState)
        {
            titleText.setTextColor(onClickListener.getActiveColour())
        }
        else
        {
            titleText.setTextColor(onClickListener.getInactiveColour())
        }

        titleText.text = item.title
    }
}