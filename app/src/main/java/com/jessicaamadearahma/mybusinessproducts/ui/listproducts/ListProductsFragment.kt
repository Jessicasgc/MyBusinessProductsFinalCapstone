package com.jessicaamadearahma.mybusinessproducts.ui.listproducts

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jessicaamadearahma.mybusinessproducts.R
import com.jessicaamadearahma.mybusinessproducts.core.adapter.ListProductsAdapter
import com.jessicaamadearahma.mybusinessproducts.core.data.Resource
import com.jessicaamadearahma.mybusinessproducts.databinding.FragmentListProductsBinding
import com.jessicaamadearahma.mybusinessproducts.ui.productdetail.ProductDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListProductsFragment : Fragment() {

    private val listProductViewModel : ListProductsViewModel by viewModel()
    private var _binding: FragmentListProductsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productAdapter = ListProductsAdapter {
            startActivity(Intent(activity, ProductDetailActivity::class.java).putExtra(ProductDetailActivity.DETAIL_DATA, it))
        }
        binding.apply{
            listProductViewModel.products.observe(viewLifecycleOwner) {products ->
                when(products){
                    is Resource.Loading -> {
                        loading.visibility = View.VISIBLE
                        tvError.visibility = View.GONE
                    }

                    is Resource.Success -> {
                        loading.visibility = View.GONE
                        tvError.visibility = View.GONE
                        productAdapter.setProductsData(products.data)
                        rvProducts.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                        rvProducts.setHasFixedSize(true)
                        rvProducts.adapter = productAdapter
                    }
                    is Resource.Error -> {
                        loading.visibility = View.GONE
                        tvError.visibility = View.VISIBLE
                        tvError.text = products.message
                        Snackbar.make(binding.root, getString(R.string.error), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        fun newInstance() = ListProductsFragment()
    }
}