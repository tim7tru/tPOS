package com.timmytruong.timmypos.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.timmytruong.timmypos.utils.constants.PreferenceConstants

class PreferenceUtils
{
    companion object
    {
        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: PreferenceUtils? = null

        private val LOCK = Any()

        operator fun invoke(context: Context): PreferenceUtils = instance ?: synchronized(LOCK) {
            instance ?: buildHelper(context).also {
                instance = it
            }
        }

        private fun buildHelper(context: Context): PreferenceUtils
        {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)

            return PreferenceUtils()
        }
    }

    fun saveUpdateTime(time: Long)
    {
        prefs?.edit(commit = true) {
            putLong(PreferenceConstants.MENU_TIME, time)
        }
    }

    fun getCacheDuration() = prefs?.getString(PreferenceConstants.MENU_CACHE_DURATION, null)

    fun getUpdateTime() = prefs?.getLong(PreferenceConstants.MENU_TIME, 0)
}