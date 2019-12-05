package com.timmytruong.timmypos.firebase.mapper

import com.google.firebase.database.DataSnapshot
import com.timmytruong.timmypos.firebase.interfaces.IMapper

abstract class FirebaseMapper<Entity, Model> : IMapper<Entity, Model>
{
    @Suppress("UNCHECKED_CAST")
    private fun map(dataSnapshot: DataSnapshot): Model
    {
        val entity: Entity? = dataSnapshot.value as Entity
        return map(entity!!)
    }

    fun mapList(dataSnapshot: DataSnapshot): List<Model>
    {
        val list: ArrayList<Model> = ArrayList()
        for (item: DataSnapshot in dataSnapshot.children)
        {
            list.add(map(item))
        }
        return list
    }
}