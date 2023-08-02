package com.jessicaamadearahma.mybusinessproducts.core.domain.usecase

import com.jessicaamadearahma.mybusinessproducts.core.data.Resource
import com.jessicaamadearahma.mybusinessproducts.core.domain.model.Products
import kotlinx.coroutines.flow.Flow

interface ProductsUseCase {
    fun getAllProducts(): Flow<Resource<List<Products>>>
    fun getFavProducts(): Flow<List<Products>>
    fun setFavProducts(products: Products, favState: Boolean)
}