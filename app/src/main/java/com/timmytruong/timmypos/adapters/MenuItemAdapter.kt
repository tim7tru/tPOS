package com.timmytruong.timmypos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.databinding.ItemMenuElementBinding
import com.timmytruong.timmypos.interfaces.MenuItemAddClickListener
import com.timmytruong.timmypos.model.MenuItem

class MenuItemAdapter(
        private val menuItems: ArrayList<MenuItem>,
        private val menuItemAddClickListener: MenuItemAddClickListener
) : RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>()
{
    inner class MenuItemViewHolder(var view: ItemMenuElementBinding) :
            RecyclerView.ViewHolder(view.root)

    private lateinit var animation: Animation

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int)
    {
        val item: MenuItem = menuItems[position]

        holder.view.item = item

        holder.view.addToOrderButton.setOnClickListener {
            holder.view.addToOrderButton.startAnimation(animation)

            menuItemAddClickListener.onAddToOrderButtonClicked(it, menuItems[position])
        }
    }

    override fun getItemCount(): Int
    {
        return menuItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)

        val view = DataBindingUtil.inflate<ItemMenuElementBinding>(
                inflater,
                R.layout.item_menu_element,
                parent,
                false
        )

        animation = AnimationUtils.loadAnimation(parent.context, R.anim.button_click_anim)

        return MenuItemViewHolder(view)
    }
}