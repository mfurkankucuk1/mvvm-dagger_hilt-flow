package com.example.mvvm_daggerhilt_flow.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_daggerhilt_flow.Product
import com.example.mvvm_daggerhilt_flow.data.NetworkResult
import com.example.mvvm_daggerhilt_flow.databinding.ActivityMainBinding
import com.example.mvvm_daggerhilt_flow.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val productAdapter: ProductAdapter = ProductAdapter()
    private var productList = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        setupSearch()
        subscribeObserve()
    }

    private fun setupSearch() {
        binding.etSearch.doOnTextChanged { inputText, _, _, _ ->
            if (inputText != null) {
                if (inputText.isEmpty()) {
                    if (productList.size != 0)
                        productAdapter.differ.submitList(productList)
                } else {

                    val currentTextLength = inputText.length
                    if (currentTextLength >= 2) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val productFilterList =
                                productList.filter { it.title.lowercase().contains(inputText) }
                            productAdapter.differ.submitList(productFilterList)
                        }, 500)
                    }
                }
            }
        }
    }

    private fun subscribeObserve() {
        mainViewModel.productResponse.observe(this) {
            when (it) {
                is NetworkResult.Error -> {
                    /**
                     * If there is a problem or error in the request response,
                     * the part to be displayed on the screen
                     * **/
                    hideLoading()
                    Toast.makeText(this, it.errorMessage, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    showLoading()
                }

                is NetworkResult.Success -> {
                    productList.clear()
                    hideLoading()
                    it.let { result ->
                        try {
                            productList.addAll(result.data)
                            productAdapter.differ.submitList(result.data)
                        } catch (e: Throwable) {
                            Log.e("CATCH", e.message.toString())
                        }
                    }
                }
            }
        }
    }

    private fun setupAdapter() {
        binding.apply {
            rvProduct.apply {
                adapter = productAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
    }
}