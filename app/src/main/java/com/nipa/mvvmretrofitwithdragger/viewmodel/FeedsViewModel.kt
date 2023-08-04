package com.nipa.mvvmretrofitwithdragger.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipa.mvvmretrofitwithdragger.globle.Globle.Companion.isNetworkAvailable
import com.nipa.mvvmretrofitwithdragger.model.Feeds
import com.nipa.mvvmretrofitwithdragger.model.Resource
import com.nipa.mvvmretrofitwithdragger.retrofit.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor(private val apiService: ApiService,@ApplicationContext private val context: Context)  : ViewModel(){
    private val _feeds = MutableLiveData<Resource<List<Feeds>>>()
    val feeds: LiveData<Resource<List<Feeds>>>  = _feeds
    init {
        callFeedsApi()
    }
   fun callFeedsApi(){
       _feeds.postValue(Resource.Loading())

       viewModelScope.launch(Dispatchers.IO) {
           if (isNetworkAvailable(context)) {
               try {
                   val response = apiService.getPosts()
                   _feeds.postValue(Resource.Success(response,"Data success fetch"))
               }catch (e: IOException) {
                   // Handle network error (e.g., no internet connection)
                   _feeds.postValue(Resource.Error("Network error: ${e.message}"))
               } catch (e: Exception) {
                   // Handle error
                   _feeds.postValue(Resource.Error("Error : ${e.message}"))
               }
           }else{
               _feeds.postValue(Resource.Error("Please check Network."))
           }


       }

    }
}