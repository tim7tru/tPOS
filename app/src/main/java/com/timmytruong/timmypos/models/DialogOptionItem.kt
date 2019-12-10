package com.timmytruong.timmypos.models

data class DialogOptionItem(var checkedStatus: Boolean = false,
                            var name: String = "",
                            var cost: String = "",
                            var category: String = "",
                            var optionTag: String = "")
{
    fun resetChecked()
    {
        checkedStatus = false
    }
}