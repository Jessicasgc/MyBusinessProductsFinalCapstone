package com.jessicaamadearahma.mybusinessproducts.favoriteproducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jessicaamadearahma.mybusinessproducts.core.domain.usecase.ProductsUseCase

class FavoriteProductsViewModel (productsUseCase: ProductsUseCase): ViewModel(){
    val favProducts = productsUseCase.getFavProducts().asLiveData()
}