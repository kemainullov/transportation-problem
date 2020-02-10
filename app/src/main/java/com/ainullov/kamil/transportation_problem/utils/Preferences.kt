package com.ainullov.kamil.transportation_problem.utils

import android.content.Context
import android.content.SharedPreferences
import com.ainullov.kamil.transportation_problem.R
import com.ainullov.kamil.transportation_problem.domain.entities.TransportationProblemData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Preferences(context: Context) {
    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    fun getCustomString(key: String): String = sharedPreferences.getString(key, "") ?: ""

    fun setCustomString(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getCustomStringSet(key: String): Set<String>? = sharedPreferences.getStringSet(key, null)

    fun setCustomStringSet(key: String, value: Set<String>) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

    fun getCustomBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)

    fun setCustomBoolean(key: String, value: Boolean) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getCustomInt(key: String): Int = sharedPreferences.getInt(key, -1)

    fun setCustomInt(key: String, value: Int) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getCustomLong(key: String): Long = sharedPreferences.getLong(key, -1)

    fun setCustomLong(key: String, value: Long) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getCustomFloat(key: String): Float = sharedPreferences.getFloat(key, 0F)

    fun setCustomFLoat(key: String, value: Float) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    fun getCustomIntArray(key: String): IntArray? {
        return Gson().fromJson(
            sharedPreferences.getString(key, null),
            object : TypeToken<IntArray>() {}.type
        )
    }

    fun setCustomIntArray(key: String, value: IntArray) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, Gson().toJson(value))
        editor.apply()
    }

    fun getCustomArrayOfDoubleArrays(key: String): Array<DoubleArray>? {
        return Gson().fromJson(
            sharedPreferences.getString(key, null),
            object : TypeToken<Array<DoubleArray>>() {}.type
        )
    }

    fun setCustomArrayOfDoubleArrays(key: String, value: Array<DoubleArray>) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, Gson().toJson(value))
        editor.apply()
    }

    fun setTransportationProblemData(transportationProblemData: TransportationProblemData) {
        setCustomIntArray(Const.PrefKeys.SUPPLY, transportationProblemData.supply)
        setCustomIntArray(Const.PrefKeys.DEMAND, transportationProblemData.demand)
        setCustomArrayOfDoubleArrays(Const.PrefKeys.COSTS, transportationProblemData.costs)
    }

    fun removeTransportationProblemData() {
        removePref(Const.PrefKeys.SUPPLY)
        removePref(Const.PrefKeys.DEMAND)
        removePref(Const.PrefKeys.COSTS)
    }

    fun removePref(key: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove(key)
        editor.apply()
    }

    fun clear() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun hasPref(key: String): Boolean = sharedPreferences.contains(key)
}