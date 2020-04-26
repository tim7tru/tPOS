package com.timmytruong.timmypos.model

data class OrderedItem(var menuNumber: Int,
                       var name: String,
                       var size: String? = null,
                       var extras: ArrayList<String>? = null,
                       var broth: String? = null,
                       var quantity: Int,
                       var unitCost: Double)