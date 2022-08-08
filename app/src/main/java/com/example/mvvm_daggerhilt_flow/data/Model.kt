package com.example.mvvm_daggerhilt_flow

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * Created by M.Furkan KÜÇÜK on 8.08.2022
 **/
data class Product(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("price") var price: Double,
    @SerializedName("description") var description: String,
    @SerializedName("category") var category: String,
    @SerializedName("image") var image: String,
    @SerializedName("rating") var rating: Rating? = Rating()
)


data class Rating(
    @SerializedName("rate") var rate: Double? = null,
    @SerializedName("count") var count: Int? = null
)