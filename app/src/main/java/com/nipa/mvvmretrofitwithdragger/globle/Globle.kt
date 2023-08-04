package com.nipa.mvvmretrofitwithdragger.globle

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.TextUtils
import android.util.Patterns
import com.nipa.mvvmretrofitwithdragger.globle.Pref.password
import com.nipa.mvvmretrofitwithdragger.globle.Pref.rememberMe
import com.nipa.mvvmretrofitwithdragger.globle.Pref.userId

class Globle {
    companion object {
            lateinit var  prefs: SharedPreferences
            fun getPreferenceString(context: Context,data:String):String?{
                prefs = Pref.callPrefs(context)
                if(data.equals("password")){
                    return prefs.password
                }else if(data.equals("userId")){
                    return prefs.userId
                }
                return null
            }
        fun getPreferenceBoolean(context: Context,data:String):Boolean{
            prefs = Pref.callPrefs(context)
            if(data.equals("remember")){
                return prefs.rememberMe
            }
            return false
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)

            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        }

         fun isUserNameValid(username: String): Boolean {
            return if (username.contains("@")) {
                Patterns.EMAIL_ADDRESS.matcher(username).matches()
            } else {
                username.isNotBlank()
            }
        }
    }
}