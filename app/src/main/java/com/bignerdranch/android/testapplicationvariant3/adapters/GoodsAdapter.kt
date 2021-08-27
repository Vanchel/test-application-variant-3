package com.bignerdranch.android.testapplicationvariant3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.testapplicationvariant3.R
import com.bignerdranch.android.testapplicationvariant3.databinding.ListItemGoodsBinding
import com.bignerdranch.android.testapplicationvariant3.domain.Goods
import com.bumptech.glide.Glide

class GoodsAdapter(
    private val cartCallback: CartCallback,
    private val onSelected: (goods: Goods) -> Unit
) : ListAdapter<Goods, GoodsAdapter.ViewHolder>(GoodsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onSelected, cartCallback)
    }

    class ViewHolder private constructor(private val binding: ListItemGoodsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Goods, onSelected: (goods: Goods) -> Unit, cartCallback: CartCallback) {
            binding.goods = item

            Glide.with(binding.root)
                .load(item.imageUrl)
                .placeholder(R.drawable.placeholder)
                .into(binding.image)

            binding.card.setOnClickListener {
                onSelected.invoke(item)
            }
            binding.cartButton.setOnClickListener {
                if (item.count > 0) {
                    cartCallback.onRemoveFromCart(item)
                } else {
                    cartCallback.onAddToCart(item)
                }
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemGoodsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    interface CartCallback {
        fun onAddToCart(goods: Goods)
        fun onRemoveFromCart(goods: Goods)
    }
}

class GoodsDiffCallback : DiffUtil.ItemCallback<Goods>() {
    override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean {
        return oldItem == newItem
    }
}