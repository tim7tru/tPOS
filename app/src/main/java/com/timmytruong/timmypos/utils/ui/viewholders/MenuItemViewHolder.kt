package com.timmytruong.timmypos.utils.ui.viewholders

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.models.MenuItem

class MenuItemViewHolder(itemView: View,
                         private val menuItemAddClickListener: MenuItemAddClickListener): RecyclerView.ViewHolder(itemView)
{
    private val itemDescriptionText: TextView = itemView.findViewById(R.id.item_description)
    private val itemTitleText: TextView = itemView.findViewById(R.id.item_name)
    private val itemCostText: TextView = itemView.findViewById(R.id.item_cost)
    private val itemImage: ImageView = itemView.findViewById(R.id.item_picture)
    private val addToOrderButton: Button = itemView.findViewById(R.id.add_to_order_button)

    fun setDetails(item: MenuItem)
    {
        itemDescriptionText.text = item.description
        itemTitleText.text = item.name
        itemCostText.text = String.format("$%s ea.", item.cost)

        addToOrderButton.setOnClickListener {
            menuItemAddClickListener.onAddToOrderButtonClicked(it!!, this.layoutPosition)
        }

    }
}