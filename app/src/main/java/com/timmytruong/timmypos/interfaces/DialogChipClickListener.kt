package com.timmytruong.timmypos.interfaces

import android.view.View
import com.google.android.material.chip.ChipGroup

interface DialogChipClickListener
{
    fun onSizeChipClicked(chipGroup: ChipGroup, i: Int): ChipGroup.OnCheckedChangeListener

    fun onBrothChipClicked(chipGroup: ChipGroup, i: Int): ChipGroup.OnCheckedChangeListener

    fun onExtraChipClicked(chipGroup: ChipGroup, i: Int): ChipGroup.OnCheckedChangeListener
}