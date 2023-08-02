package com.jessicaamadearahma.mybusinessproducts.core.di

import androidx.room.Room
import com.jessicaamadearahma.mybusinessproducts.core.data.ProductsRepository
import com.jessicaamadearahma.mybusinessproducts.core.data.retrofit.ApiService
import com.jessicaamadearahma.mybusinessproducts.core.data.retrofit.RetrofitRemoteDataSource
import com.jessicaamadearahma.mybusinessproducts.core.data.room.ProductsDatabase
import com.jessicaamadearahma.mybusinessproducts.core.data.room.RoomLocalDataSource
import com.jessicaamadearahma.mybusinessproducts.core.domain.repository.IProductsRepository
import com.jessicaamadearahma.mybusinessproducts.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<ProductsDatabase>().productsDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("jessica".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            ProductsDatabase::class.java, "Product.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory).build()
    }
}

val networkModule = module {
    single {
        val hostname = "mocki.io"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/4Yev5mbAIonVQq6wHT7i4FjRe+JtKJcWpqSCGac6+Jk=")
            .add(hostname, "sha256/DxH4tt40L+eduF6szpY6TONlxhZhBd+pJ9wbHlQ2fuw=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(if(BuildConfig.DEBUG) { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }else { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE) })
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mocki.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { RoomLocalDataSource(get()) }
    single { RetrofitRemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IProductsRepository> {
        ProductsRepository(
            get(),
            get(),
            get()
        )
    }
}