package com.timmytruong.timmypos.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.timmytruong.timmypos.interfaces.FirebaseDatabaseRepositoryCallback

abstract class FirebaseDatabaseRepository<Model>(private val mapper: FirebaseMapper)
{
    private var databaseReference: DatabaseReference
    private lateinit var firebaseCallback: FirebaseDatabaseRepositoryCallback<Model>
    private lateinit var listener: BaseValueEventListener

    protected abstract fun getRootNode(): String

    init
    {
        databaseReference = FirebaseDatabase.getInstance().getReference(getRootNode())
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