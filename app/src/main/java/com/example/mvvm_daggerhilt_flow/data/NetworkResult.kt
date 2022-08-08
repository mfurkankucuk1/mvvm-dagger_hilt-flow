package com.example.mvvm_daggerhilt_flow.data

/**
 * Created by M.Furkan KÜÇÜK on 8.08.2022
 **/
sealed class NetworkResult<T> {
    data class Loading<T>(val isLoading:Boolean): NetworkResult<T>()
    data class Success<T>(val data:T): NetworkResult<T>()
    data class Error<T>(val errorMessage:String): NetworkResult<T>()
}