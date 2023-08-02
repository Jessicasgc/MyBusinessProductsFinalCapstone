package com.jessicaamadearahma.mybusinessproducts.ui.productdetail

import androidx.lifecycle.ViewModel
import com.jessicaamadearahma.mybusinessproducts.core.domain.model.Products
import com.jessicaamadearahma.mybusinessproducts.core.domain.usecase.ProductsUseCase

class ProductDetailViewModel(private val productsUseCase : ProductsUseCase): ViewModel() {
    fun setFavProducts(products: Products, favState: Boolean) = productsUseCase.setFavProducts(products, favState)
}