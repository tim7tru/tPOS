package com.timmytruong.timmypos.model

data class OrderedItem(
        var menuNumber: Int,
        var name: String,
        var size: DialogOptionItem? = null,
        var extras: ArrayList<DialogOptionItem>? = null,
        var broth: DialogOptionItem? = null,
        var quantity: Int = -1,
        var unitCost: Double = -1.0,
        var notes: String = ""
)