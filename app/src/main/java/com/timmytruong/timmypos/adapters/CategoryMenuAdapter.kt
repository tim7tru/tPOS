package com.timmytruong.timmypos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.CategoryMenuItemClickListener
import com.timmytruong.timmypos.models.CategoryMenuItem
import com.timmytruong.timmypos.utils.ui.viewholders.CategoryMenuViewHolder

class CategoryMenuAdapter(private val context: Context,
                          private val categoryTitles: ArrayList<CategoryMenuItem>,
                          private val onClickListener: CategoryMenuItemClickListener): RecyclerView.Adapter<CategoryMenuViewHolder>()
{
    private var activePosition: Int = 0

    fun getActivePosition(): Int
    {
        return activePosition
    }

    fun setActivePosition(position: Int)
    {
        activePosition = position
    }

    override fun onBindViewHolder(holder: CategoryMenuViewHolder, position: Int)
    {
        val item: CategoryMenuItem = categoryTitles[position]
        holder.setDetails(item)
    }

    override fun getItemCount(): Int
    {
        return categoryTitles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMenuViewHolder
    {
        return CategoryMenuViewHolder(
            LayoutInflater.from(context).inflate(R.layout.category_menu_item, parent, false),
            onClickListener
        )
    }
}