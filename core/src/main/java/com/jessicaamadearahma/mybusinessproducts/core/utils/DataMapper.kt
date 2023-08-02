package com.jessicaamadearahma.mybusinessproducts.core.utils

import com.jessicaamadearahma.mybusinessproducts.core.data.retrofit.pojo.ProductResponse
import com.jessicaamadearahma.mybusinessproducts.core.data.room.ProductsEntity
import com.jessicaamadearahma.mybusinessproducts.core.domain.model.Products

object DataMapper {
    fun mapResponsesToEntities(input: List<ProductResponse>): List<ProductsEntity> {
        val productList = ArrayList<ProductsEntity>()
        input.map {
            val product = ProductsEntity(
                productId = it.id,
                image = it.image,
                price = it.price,
                description = it.description,
                title = it.title,
                category = it.category,
                favorite = false
            )
            productList.add(product)
        }
        return productList
    }

    fun mapEntitiesToDomain(input: List<ProductsEntity>): List<Products> =
        input.map {
            Products(
                productId = it.productId,
                image = it.image,
                price = it.price,
                description = it.description,
                title = it.title,
                category = it.category,
                favorite = it.favorite
            )
        }

    fun mapDomainToEntity(input: Products) = ProductsEntity(
        productId = input.productId,
        image = input.image,
        price = input.price,
        description = input.description,
        title = input.title,
        category = input.category,
        favorite = input.favorite
    )
}