package com.jessicaamadearahma.mybusinessproducts.core.data.retrofit

import android.util.Log
import com.jessicaamadearahma.mybusinessproducts.core.data.Resource
import com.jessicaamadearahma.mybusinessproducts.core.data.retrofit.pojo.ProductResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RetrofitRemoteDataSource (private val apiService: ApiService){
    suspend fun getAllProducts(): Flow<ApiResponse<List<ProductResponse>>> {
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.products
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}