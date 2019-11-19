package com.timmytruong.timmypos.models

data class MenuItem(private var description: String,
               private var title: String,
               private var cost: String)
{
    fun getTitle(): String
    {
        return title
    }

    fun getDescription(): String
    {
        return description
    }

    fun getCost(): String
    {
        return cost
    }

    fun setTitle(title: String)
    {
        this.title = title
    }

    fun setDescription(description: String)
    {
        this.description = description
    }

    fun setCost(cost: String)
    {
        this.cost = cost
    }
}