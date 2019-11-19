package com.timmytruong.timmypos.models

class CategoryMenuItem(private var title: String, private var activeState: Boolean)
{
    fun getTitle(): String
    {
        return title
    }

    fun getActiveState(): Boolean
    {
        return activeState
    }

    fun setTitle(title: String)
    {
        this.title = title
    }

    fun setActiveState(activeState: Boolean)
    {
        this.activeState = activeState
    }
}