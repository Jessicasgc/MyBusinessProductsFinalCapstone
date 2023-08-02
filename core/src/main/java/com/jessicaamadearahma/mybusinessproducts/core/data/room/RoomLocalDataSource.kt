package com.jessicaamadearahma.mybusinessproducts.core.data.room

import kotlinx.coroutines.flow.Flow

class RoomLocalDataSource (private val productDao: ProductsDao){

    fun getAllProducts(): Flow<List<ProductsEntity>> = productDao.getAllProducts()
    fun getFavProducts(): Flow<List<ProductsEntity>> = productDao.getFavProducts()
    suspend fun insertProducts(productList: List<ProductsEntity>) = productDao.insertProducts(productList)
    fun setFavProduct(product: ProductsEntity, favState: Boolean) {
        product.favorite = favState
        productDao.updateFavProducts(product)
    }
}