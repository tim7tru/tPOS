package com.timmytruong.timmypos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.models.MenuItem
import com.timmytruong.timmypos.utils.ui.MenuItemViewHolder

class MenuItemAdapter(private val context: Context,
                      private val menuDescriptions: ArrayList<MenuItem>,
                      private val menuItemAddClickListener: MenuItemAddClickListener): RecyclerView.Adapter<MenuItemViewHolder>()
{
    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int)
    {
        val item: MenuItem = menuDescriptions[position]
        holder.setDetails(item)
    }

    override fun getItemCount(): Int
    {
        return menuDescriptions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder
    {
        return MenuItemViewHolder(LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false), menuItemAddClickListener)
    }
}