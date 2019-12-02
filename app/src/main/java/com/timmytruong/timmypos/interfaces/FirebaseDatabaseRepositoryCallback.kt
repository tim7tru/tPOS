package com.timmytruong.timmypos.interfaces

import java.lang.Exception

interface FirebaseDatabaseRepositoryCallback<T>
{
    fun onSuccess(result: List<T>)

    fun onError(e: Exception)
}