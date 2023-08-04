package com.nipa.mvvmretrofitwithdragger

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.nipa.mvvmretrofitwithdragger.databinding.ActivityMainBinding
import com.nipa.mvvmretrofitwithdragger.globle.Pref
import com.nipa.mvvmretrofitwithdragger.globle.Pref.password
import com.nipa.mvvmretrofitwithdragger.globle.Pref.rememberMe
import com.nipa.mvvmretrofitwithdragger.globle.Pref.userId
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    //lateinit var  prefs: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*prefs = Pref.callPrefs(this)
        val username=prefs.userId
        val password=prefs.password
        val rememberMe=prefs.rememberMe*/


    }
}