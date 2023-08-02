package com.jessicaamadearahma.mybusinessproducts.core.data.retrofit

import com.jessicaamadearahma.mybusinessproducts.core.data.retrofit.pojo.ListProductsResponse
import retrofit2.http.GET

interface ApiService {
    @GET("e971d644-919b-4418-8e5b-b3ea09cea2d1")
    suspend fun getList(): ListProductsResponse
}