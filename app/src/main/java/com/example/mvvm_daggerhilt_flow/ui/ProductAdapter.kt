package com.example.mvvm_daggerhilt_flow.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvm_daggerhilt_flow.Product
import com.example.mvvm_daggerhilt_flow.databinding.ItemRowProductBinding

/**
 * Created by M.Furkan KÜÇÜK on 8.08.2022
 **/
class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemRowProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemRowProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.binding.apply {
            tvProductName.text = currentItem.title
            tvProductDescription.text = currentItem.description
            Glide.with(holder.itemView.context).load(currentItem.image).into(imgProduct)
            currentItem.rating?.let {
                it.rate?.let { rate ->
                    ratingProduct.rating = rate.toFloat()
                }
            }
        }
    }

    override fun getItemCount() = differ.currentList.size
}