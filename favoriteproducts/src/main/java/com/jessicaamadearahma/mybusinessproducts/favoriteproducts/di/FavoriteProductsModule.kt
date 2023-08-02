package com.jessicaamadearahma.mybusinessproducts.favoriteproducts.di

import com.jessicaamadearahma.mybusinessproducts.favoriteproducts.FavoriteProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteProductsModule = module {
    viewModel { FavoriteProductsViewModel(get()) }
}