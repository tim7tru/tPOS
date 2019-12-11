package com.timmytruong.timmypos.model

data class MenuItem(var menuNumber: Int = -1,
                    var availablity: Boolean = false,
                    var cost: String = "",
                    var description: String = "",
                    var dialogType: String = "",
                    var name: String = "",
                    var tags: ArrayList<String>? = null)
