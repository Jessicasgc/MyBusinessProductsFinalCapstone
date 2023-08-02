package com.jessicaamadearahma.mybusinessproducts.ui.productdetail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.jessicaamadearahma.mybusinessproducts.R
import com.jessicaamadearahma.mybusinessproducts.core.domain.model.Products
import com.jessicaamadearahma.mybusinessproducts.databinding.ActivityProductDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductDetailActivity : AppCompatActivity() {

    private val productDetailViewModel: ProductDetailViewModel by viewModel()
    private lateinit var binding : ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val detailProduct = intent.getParcelableExtra<Products>(DETAIL_DATA)

        binding.apply {
            detailProduct?.let {
                Glide.with(this@ProductDetailActivity)
                    .load(detailProduct.image)
                    .into(binding.ivDetailImage)

                tvDetailTitle.text = detailProduct.title
                tvDetailPrice.text = getString(R.string.price) + detailProduct.price
                tvDetailCategory.text = getString(R.string.category, detailProduct.category)
                tvDetailDescription.text = detailProduct.description
                var favState = detailProduct.favorite
                setFavState(favState)
                fabFavoritesDynamic.setOnClickListener {
                    favState = !favState
                    productDetailViewModel.setFavProducts(detailProduct, favState)
                    setFavState(favState)
                }
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
    private fun setFavState(favState: Boolean) {
        if (favState){
            binding.fabFavoritesDynamic.setImageResource(R.drawable.ic_favorite)
        }else{
            binding.fabFavoritesDynamic.setImageResource(R.drawable.ic_favorite_border)
        }
    }
    companion object{
        const val DETAIL_DATA = "detail_data"
    }
}