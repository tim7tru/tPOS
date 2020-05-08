package com.timmytruong.timmypos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.databinding.ItemCategoryMenuElementBinding
import com.timmytruong.timmypos.interfaces.MenuCategoryEventListener
import com.timmytruong.timmypos.model.CategoryMenuItem

class CategoryMenuAdapter(
        private val categoryTitles: ArrayList<CategoryMenuItem>,
        private val menuCategoryEventListener: MenuCategoryEventListener
) : RecyclerView.Adapter<CategoryMenuAdapter.CategoryMenuViewHolder>()
{
    inner class CategoryMenuViewHolder(val view: ItemCategoryMenuElementBinding) :
            RecyclerView.ViewHolder(view.root)

    var activePosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMenuViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)

        val dataBinding = DataBindingUtil.inflate<ItemCategoryMenuElementBinding>(
                inflater,
                R.layout.item_category_menu_element,
                parent,
                false
        )

        return CategoryMenuViewHolder(dataBinding)
    }

    override fun getItemId(position: Int): Long
    {
        return categoryTitles[position].id.toLong()
    }

    override fun onBindViewHolder(holder: CategoryMenuViewHolder, position: Int)
    {
        holder.view.category = categoryTitles[position]

        holder.view.categoryMenuTitle.setOnClickListener {
            menuCategoryEventListener.onCategoryMenuItemClicked(
                    view = it,
                    oldPosition = activePosition,
                    newPosition = holder.layoutPosition
            )
        }
    }

    override fun getItemCount(): Int
    {
        return categoryTitles.size
    }
}