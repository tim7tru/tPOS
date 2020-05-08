package com.timmytruong.timmypos.model

data class Order(
        val orderedItems: ArrayList<OrderedItem> = arrayListOf(),
        var itemCount: Int = 0
)
