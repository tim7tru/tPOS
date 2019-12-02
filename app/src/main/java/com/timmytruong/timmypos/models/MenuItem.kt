package com.timmytruong.timmypos.models

data class MenuItem(var availablity: Boolean,
                    var cost: String,
                    var description: String,
                    var dialogType: String,
                    var name: String,
                    var tags: Array<String>?)