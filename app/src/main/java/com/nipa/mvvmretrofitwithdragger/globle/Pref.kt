package com.nipa.mvvmretrofitwithdragger.globle

import android.content.Context
import android.content.SharedPreferences

object Pref {
    val USER_EMAIL = "USER_EMAIL"
    val USER_PASSWORD = "PASSWORD"
    val REMEMBER_ME="REMEMBER_ME"
    private val APP_PREF = "retrofitmvvm"
    fun callPrefs(context: Context): SharedPreferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)
    inline fun SharedPreferences.SetData(operation: (SharedPreferences.Editor) -> Unit) {
        val editData = edit()
        operation(editData)
        editData.apply()
    }
    inline fun SharedPreferences.Editor.putAnyType(pairData: Pair<String, Any?>) {
        val key = pairData.first
        val value = pairData.second
        when (value) {
            is Boolean -> putBoolean(key, value)
            is String -> putString(key, value)
           else -> error("Only primitive types can be stored in SharedPreferences")
        }
    }
    var SharedPreferences.userId
        get() = getString(USER_EMAIL, "")
        set(value) {
            SetData {
                it.putAnyType(Pair(USER_EMAIL,value))

            }
        }

    var SharedPreferences.password
        get() = getString(USER_PASSWORD, "")
        set(value) {
            SetData {
                it.putAnyType(Pair(USER_PASSWORD,value))

            }
        }
    var SharedPreferences.rememberMe
        get() = getBoolean(REMEMBER_ME, false)
        set(value) {
            SetData {
                it.putAnyType(Pair(REMEMBER_ME,value))

            }
        }

    fun clearall(context:Context){
        val  sp :SharedPreferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)
        val editor=sp.edit()
        editor.clear()
        editor.apply()
        editor.commit()
    }
}