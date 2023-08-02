package com.jessicaamadearahma.mybusinessproducts.core.domain.usecase

import com.jessicaamadearahma.mybusinessproducts.core.domain.model.Products
import com.jessicaamadearahma.mybusinessproducts.core.domain.repository.IProductsRepository

class ProductsInteractor (private val productsRepository: IProductsRepository): ProductsUseCase{
    override fun getAllProducts() = productsRepository.getAllProducts()
    override fun getFavProducts() = productsRepository.getFavProducts()
    override fun setFavProducts(product: Products, favState: Boolean) = productsRepository.setFavProducts(product, favState)
}