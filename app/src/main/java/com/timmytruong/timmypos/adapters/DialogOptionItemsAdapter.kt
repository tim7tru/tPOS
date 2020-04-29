package com.timmytruong.timmypos.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timmytruong.timmypos.R
import com.timmytruong.timmypos.interfaces.DialogItemClickListener
import com.timmytruong.timmypos.model.DialogOptionItem
import com.timmytruong.timmypos.utils.ui.viewholders.DialogOptionItemViewHolder

class DialogOptionItemsAdapter(private val context: Context,
                               private val dialogOptionItems: ArrayList<DialogOptionItem>,
                               private val dialogItemClickListener: DialogItemClickListener): RecyclerView.Adapter<DialogOptionItemViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogOptionItemViewHolder
    {
        return DialogOptionItemViewHolder(
            LayoutInflater.from(context).inflate(R.layout.alert_option_item, parent, false),
            dialogItemClickListener
        )
    }

    override fun getItemCount(): Int
    {
        return dialogOptionItems.size
    }

    override fun onBindViewHolder(holder: DialogOptionItemViewHolder, position: Int)
    {
        val item = dialogOptionItems[position]
        holder.setDetails(item)
    }

}