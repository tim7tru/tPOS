package com.timmytruong.timmypos.firebase.interfaces

import java.lang.Exception

interface FirebaseDatabaseRepositoryCallback<T>
{
    fun onSuccess(result: List<T>)

    fun onError(e: Exception)
}