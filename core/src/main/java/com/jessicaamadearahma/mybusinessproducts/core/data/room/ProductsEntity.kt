package com.jessicaamadearahma.mybusinessproducts.core.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductsEntity(
    @PrimaryKey
    @ColumnInfo("productId")
    var productId: Int,

    @ColumnInfo("image")
    var image: String,

    @ColumnInfo("price")
    var price: Double,

    @ColumnInfo("description")
    var description: String,

    @ColumnInfo("title")
    var title: String,

    @ColumnInfo("category")
    var category: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean = false
)
