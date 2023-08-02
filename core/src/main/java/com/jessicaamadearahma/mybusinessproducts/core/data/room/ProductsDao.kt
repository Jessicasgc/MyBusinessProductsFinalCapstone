package com.jessicaamadearahma.mybusinessproducts.core.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductsEntity>>

    @Query("SELECT * FROM products where favorite = 1")
    fun getFavProducts():  Flow<List<ProductsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(product: List<ProductsEntity>)

    @Update
    fun updateFavProducts(product: ProductsEntity)
}