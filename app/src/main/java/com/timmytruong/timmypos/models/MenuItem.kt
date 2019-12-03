package com.timmytruong.timmypos.models

data class MenuItem(var availablity: Boolean = false,
                    var cost: String = "",
                    var description: String = "",
                    var dialogType: String = "",
                    var name: String = "",
                    var tags: ArrayList<String>? = null)
