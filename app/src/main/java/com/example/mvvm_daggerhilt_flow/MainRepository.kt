package com.example.mvvm_daggerhilt_flow

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by M.Furkan KÜÇÜK on 8.08.2022
 **/
class MainRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getPopularMovies() = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.getMostPopularMovies()
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Error(e.message ?: "Unknown Error"))
    }


}