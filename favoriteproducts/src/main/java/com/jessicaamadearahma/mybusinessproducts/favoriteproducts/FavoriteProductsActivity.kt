package com.jessicaamadearahma.mybusinessproducts.favoriteproducts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jessicaamadearahma.mybusinessproducts.core.adapter.ListProductsAdapter
import com.jessicaamadearahma.mybusinessproducts.favoriteproducts.databinding.ActivityFavoriteProductsBinding
import com.jessicaamadearahma.mybusinessproducts.favoriteproducts.di.favoriteProductsModule
import com.jessicaamadearahma.mybusinessproducts.ui.productdetail.ProductDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules

class FavoriteProductsActivity : AppCompatActivity() {
    private val favoriteProductsViewModel: FavoriteProductsViewModel by viewModel()
    private lateinit var binding : ActivityFavoriteProductsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.fav_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadKoinModules(favoriteProductsModule)
        val productAdapter = ListProductsAdapter {
            startActivity(Intent(this, ProductDetailActivity::class.java).putExtra(ProductDetailActivity.DETAIL_DATA, it))
        }
        favoriteProductsViewModel.favProducts.observe(this){
            binding.apply {
                productAdapter.setProductsData(it)
                rvProducts.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                rvProducts.setHasFixedSize(true)
                rvProducts.adapter = productAdapter
                binding.tvEmpty.visibility = if (it.isNotEmpty()) View.GONE else View.VISIBLE
            }
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}