package com.jessicaamadearahma.mybusinessproducts.di

import com.jessicaamadearahma.mybusinessproducts.core.domain.usecase.ProductsInteractor
import com.jessicaamadearahma.mybusinessproducts.core.domain.usecase.ProductsUseCase
import com.jessicaamadearahma.mybusinessproducts.ui.listproducts.ListProductsViewModel
import com.jessicaamadearahma.mybusinessproducts.ui.productdetail.ProductDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<ProductsUseCase> { ProductsInteractor(get()) }
}

val viewModelModule = module {
    viewModel { ListProductsViewModel(get()) }
    viewModel { ProductDetailViewModel(get()) }
}