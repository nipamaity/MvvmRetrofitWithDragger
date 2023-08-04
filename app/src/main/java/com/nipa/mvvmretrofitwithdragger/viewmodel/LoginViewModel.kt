package com.nipa.mvvmretrofitwithdragger.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipa.mvvmretrofitwithdragger.globle.Globle.Companion.prefs
import com.nipa.mvvmretrofitwithdragger.globle.Pref
import com.nipa.mvvmretrofitwithdragger.globle.Pref.password
import com.nipa.mvvmretrofitwithdragger.globle.Pref.rememberMe
import com.nipa.mvvmretrofitwithdragger.globle.Pref.userId
import com.nipa.mvvmretrofitwithdragger.model.Feeds
import com.nipa.mvvmretrofitwithdragger.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(@ApplicationContext val context: Context)  : ViewModel() {
    private val _login = MutableLiveData<Resource<String>>()
    val login: LiveData<Resource<String>> = _login
        fun login(userID: String,password:String, isChecked:Boolean){
            _login.postValue(Resource.Loading())
            viewModelScope.launch(Dispatchers.IO) {
                try {
                         val prefs = Pref.callPrefs(context)
                        if(isChecked){
                            // set data to preference
                            prefs.userId = userID
                            prefs.rememberMe = isChecked
                            prefs.password = password

                        }else{
                            Pref.clearall(context)
                        }
                    _login.postValue(Resource.Success("Success","Data success fetch"))
                }catch (e:Exception){
                    _login.postValue(Resource.Error("Error : ${e.message}"))
                }
            }


        }

}