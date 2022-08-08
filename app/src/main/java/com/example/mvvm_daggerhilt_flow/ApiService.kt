package com.example.mvvm_daggerhilt_flow

import retrofit2.http.GET

/**
 * Created by M.Furkan KÜÇÜK on 8.08.2022
 **/
interface ApiService {

    @GET("products")
    suspend fun getMostPopularMovies() : List<Product>
}