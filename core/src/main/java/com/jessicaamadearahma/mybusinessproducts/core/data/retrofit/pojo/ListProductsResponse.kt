package com.jessicaamadearahma.mybusinessproducts.core.data.retrofit.pojo

import com.google.gson.annotations.SerializedName

data class ListProductsResponse(
    @field:SerializedName("products")
    val products: List<ProductResponse>
)
