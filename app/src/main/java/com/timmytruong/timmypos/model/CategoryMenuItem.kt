package com.timmytruong.timmypos.model

import androidx.databinding.ObservableBoolean

data class CategoryMenuItem(
        var id: Int = -1,
        var name: String = "",
        var isActive: Boolean = false
)