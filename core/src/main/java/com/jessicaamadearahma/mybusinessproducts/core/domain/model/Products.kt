package com.jessicaamadearahma.mybusinessproducts.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Products(
    val productId: Int,
    val image: String,
    val price: Double,
    val description: String,
    val title: String,
    val category: String,
    var favorite: Boolean
): Parcelable
