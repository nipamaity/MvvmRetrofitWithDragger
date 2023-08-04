package com.nipa.mvvmretrofitwithdragger.retrofit

import com.nipa.mvvmretrofitwithdragger.model.Feeds
import retrofit2.http.GET

interface ApiService {
    @GET("photos")
    suspend fun getPosts(): List<Feeds>
}