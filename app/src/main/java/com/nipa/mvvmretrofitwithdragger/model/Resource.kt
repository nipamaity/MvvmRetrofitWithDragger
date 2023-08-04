package com.nipa.mvvmretrofitwithdragger.model

sealed class Resource<T>(
//parameters
    val data: T?=null,
    val message: String?= null,
    ){
    //classes allowed to inherit
    class Success<T>(data: T,message:String) : Resource<T>(data,message)
    class Error<T>(message: String): Resource<T>(null, message)
    class Loading<T>: Resource<T>()
}
