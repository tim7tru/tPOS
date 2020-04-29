package com.timmytruong.timmypos.provider

import com.timmytruong.timmypos.model.DialogOptionItem

class SoupsExtrasProvider(
    private val soupsExtrasArray: ArrayList<DialogOptionItem> = arrayListOf()
)
{
    fun getSoupsExtras(): ArrayList<DialogOptionItem>
    {
        return soupsExtrasArray
    }

    fun onSoupsExtrasRetrieved(soupsExtras: List<DialogOptionItem>)
    {
        soupsExtrasArray.clear()

        for (index in soupsExtras.indices)
        {
            soupsExtrasArray.add(soupsExtras[index])
        }
    }
}
