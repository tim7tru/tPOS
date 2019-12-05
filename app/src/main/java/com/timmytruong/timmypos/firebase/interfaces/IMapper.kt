package com.timmytruong.timmypos.firebase.interfaces

interface IMapper<From, To>
{
    fun map(from: From): To
}