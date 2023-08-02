package com.jessicaamadearahma.mybusinessproducts.core.data

import com.jessicaamadearahma.mybusinessproducts.core.data.retrofit.ApiResponse
import com.jessicaamadearahma.mybusinessproducts.core.data.retrofit.RetrofitRemoteDataSource
import com.jessicaamadearahma.mybusinessproducts.core.data.retrofit.pojo.ProductResponse
import com.jessicaamadearahma.mybusinessproducts.core.data.room.RoomLocalDataSource
import com.jessicaamadearahma.mybusinessproducts.core.domain.model.Products
import com.jessicaamadearahma.mybusinessproducts.core.domain.repository.IProductsRepository
import com.jessicaamadearahma.mybusinessproducts.core.utils.AppExecutors
import com.jessicaamadearahma.mybusinessproducts.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductsRepository(
    private val retrofitRemoteDataSource: RetrofitRemoteDataSource,
    private val roomLocalDataSource: RoomLocalDataSource,
    private val appExecutors: AppExecutors
) : IProductsRepository{
    override fun getAllProducts(): Flow<Resource<List<Products>>> =
        object :NetworkBoundResource<List<Products>, List<ProductResponse>>() {
            override fun loadFromDB(): Flow<List<Products>> {
                return roomLocalDataSource.getAllProducts().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }
            override fun shouldFetch(data: List<Products>?): Boolean = true
            override suspend fun createCall(): Flow<ApiResponse<List<ProductResponse>>> = retrofitRemoteDataSource.getAllProducts()
            override suspend fun saveCallResult(data: List<ProductResponse>) {
                val productList = DataMapper.mapResponsesToEntities(data)
                roomLocalDataSource.insertProducts(productList)
            }
        }.asFlow()

    override fun getFavProducts(): Flow<List<Products>> {
        return roomLocalDataSource.getFavProducts().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }
    override fun setFavProducts(products: Products, favState: Boolean) {
        val productsEntity = DataMapper.mapDomainToEntity(products)
        appExecutors.diskIO().execute { roomLocalDataSource.setFavProduct(productsEntity, favState) }
    }
}