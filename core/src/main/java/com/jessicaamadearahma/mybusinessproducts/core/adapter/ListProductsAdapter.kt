package com.jessicaamadearahma.mybusinessproducts.core.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jessicaamadearahma.mybusinessproducts.core.R
import com.jessicaamadearahma.mybusinessproducts.core.databinding.ItemProductBinding
import com.jessicaamadearahma.mybusinessproducts.core.domain.model.Products

class ListProductsAdapter (private var onItemClick: (Products) -> Unit)
    : RecyclerView.Adapter<ListProductsAdapter.ListViewHolder>() {
    private var listProducts = ArrayList<Products>()

    @SuppressLint("NotifyDataSetChanged")
    fun setProductsData(newListData: List<Products>?){
        if(newListData == null) return
        listProducts.clear()
        listProducts.addAll(newListData)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false))
    override fun getItemCount() = listProducts.size
    override fun onBindViewHolder(holder: ListProductsAdapter.ListViewHolder, position: Int) {
        val data = listProducts[position]
        holder.bind(data)
    }
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemProductBinding.bind(itemView)
        fun bind(data: Products) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(data.image)
                    .into(ivImage)
                tvTitle.text = data.title
                tvDescription.text = data.description
                itemView.setOnClickListener{
                    onItemClick(data)
                }
            }
        }
    }
}