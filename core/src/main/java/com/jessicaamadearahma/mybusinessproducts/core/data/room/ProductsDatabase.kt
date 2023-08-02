package com.jessicaamadearahma.mybusinessproducts.core.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductsEntity::class], version = 1, exportSchema = false)
abstract class ProductsDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao
}