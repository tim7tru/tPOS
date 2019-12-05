package com.timmytruong.timmypos.models

data class DialogOptionItem(var checkedStatus: Boolean = false,
                            var optionTitle: String = "",
                            var unitValue: Int = -1,
                            var tag: String = "")