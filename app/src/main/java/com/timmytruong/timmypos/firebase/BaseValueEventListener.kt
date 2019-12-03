package com.timmytruong.timmypos.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.firebase.mapper.FirebaseMapper

class BaseValueEventListener<Model, Entity> (private val mapper: FirebaseMapper<Entity, Model>,
                                             private val callback: FirebaseDatabaseRepositoryCallback<Model>): ValueEventListener
{
    override fun onCancelled(p0: DatabaseError)
    {
        callback.onError(p0.toException())
    }

    override fun onDataChange(p0: DataSnapshot)
    {
        val data: List<Model> = mapper.mapList(p0)
        callback.onSuccess(data)
    }

}