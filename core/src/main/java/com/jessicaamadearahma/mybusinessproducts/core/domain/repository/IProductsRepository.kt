package com.jessicaamadearahma.mybusinessproducts.core.domain.repository

import com.jessicaamadearahma.mybusinessproducts.core.data.Resource
import com.jessicaamadearahma.mybusinessproducts.core.domain.model.Products
import kotlinx.coroutines.flow.Flow

interface IProductsRepository {
    fun getAllProducts(): Flow<Resource<List<Products>>>
    fun getFavProducts(): Flow<List<Products>>
    fun setFavProducts(products: Products, favState: Boolean)
}