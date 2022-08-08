package com.example.mvvm_daggerhilt_flow

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvm_daggerhilt_flow.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private val productAdapter: ProductAdapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        subscribeObserve()
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
                    hideLoading()
                    it.let { result ->
                        try {
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