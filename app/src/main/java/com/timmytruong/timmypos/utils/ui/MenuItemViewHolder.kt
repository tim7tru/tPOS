package com.timmytruong.timmypos.utils.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.models.MenuItem

class MenuItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
{
    private val itemDescriptionText: TextView = itemView.findViewById(R.id.item_description)

    fun setDetails(item: MenuItem)
    {
        itemDescriptionText.text = item.getDescription()
    }
}