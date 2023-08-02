package com.jessicaamadearahma.mybusinessproducts.ui.listproducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jessicaamadearahma.mybusinessproducts.core.domain.usecase.ProductsUseCase

class ListProductsViewModel(private val productsUseCase : ProductsUseCase): ViewModel() {
    val products = productsUseCase.getAllProducts().asLiveData()
}