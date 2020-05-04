package com.timmytruong.timmypos.firebase.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.timmytruong.timmypos.firebase.BaseValueEventListener
import com.timmytruong.timmypos.firebase.interfaces.FirebaseDatabaseRepositoryCallback
import com.timmytruong.timmypos.firebase.mapper.FirebaseMapper

abstract class FirebaseDatabaseRepository<Entity, Model>(private val mapper: FirebaseMapper<Entity, Model>)
{
    private var rootReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    private var databaseReference: DatabaseReference

    private lateinit var firebaseCallback: FirebaseDatabaseRepositoryCallback<Model>

    private lateinit var listener: BaseValueEventListener<Model, Entity>

    protected abstract fun getRootNode(): String

    init
    {
        databaseReference = rootReference.child(this.getRootNode())
    }

    fun postValue(value: Any)
    {
        rootReference.setValue(value)
    }

    fun postValue(child: String, key: String, value: Any)
    {
        rootReference.child(child).child(key).setValue(value)
    }

    fun getUniqueKey(): String?
    {
        return databaseReference.push().key
    }

    fun addListener(firebaseCallback: FirebaseDatabaseRepositoryCallback<Model>)
    {
        this.firebaseCallback = firebaseCallback
        listener = BaseValueEventListener(mapper, firebaseCallback)
        databaseReference.addValueEventListener(listener)
    }

    fun removeListener()
    {
        databaseReference.removeEventListener(listener)
    }
}