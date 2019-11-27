package com.timmytruong.timmypos.models

class DialogOptionItem(private var checkStatus: Boolean,
                       private var optionTitle: String,
                       private var unitValue: Int,
                       private var tag: String)
{
    fun getCheckedStatus(): Boolean
    {
        return checkStatus
    }

    fun getOptionTitle(): String
    {
        return optionTitle
    }

    fun getUnitValue(): Int
    {
        return unitValue
    }

    fun getTag(): String
    {
        return tag
    }

    fun setCheckedStatus(checkStatus: Boolean)
    {
        this.checkStatus = checkStatus
    }

    fun setOptionTitle(optionTitle: String)
    {
        this.optionTitle = optionTitle
    }

    fun setUnitValue(unitValue: Int)
    {
        this.unitValue = unitValue
    }

    fun setTag(tag: String)
    {
        this.tag = tag
    }
}