package com.example.mvvm_daggerhilt_flow.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvm_daggerhilt_flow.service.MainRepository
import com.example.mvvm_daggerhilt_flow.data.NetworkResult
import com.example.mvvm_daggerhilt_flow.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 8.08.2022
 **/
@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
):ViewModel() {
    private var _productResponse = MutableLiveData<NetworkResult<List<Product>>>()
    val productResponse: LiveData<NetworkResult<List<Product>>> = _productResponse

    init {
        fetchAllMovies()
    }

    private fun fetchAllMovies() {
        viewModelScope.launch {
            mainRepository.getPopularMovies().collect {
                it?.let {
                    _productResponse.postValue(it)
                }
            }
        }
    }

}