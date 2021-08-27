package com.bignerdranch.android.testapplicationvariant3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.testapplicationvariant3.databinding.ListItemGoodsInCartBinding
import com.bignerdranch.android.testapplicationvariant3.domain.Goods

class GoodsInCartAdapter(private val countCallback: CountCallback) :
    ListAdapter<Goods, GoodsInCartAdapter.ViewHolder>(GoodsInCartDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, countCallback)
    }

    class ViewHolder private constructor(private val binding: ListItemGoodsInCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Goods, countCallback: CountCallback) {
            binding.apply {
                goods = item

                addButton.setOnClickListener { countCallback.onAdd(item) }
                removeButton.setOnClickListener { countCallback.onRemove(item) }

                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemGoodsInCartBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    interface CountCallback {
        fun onAdd(goods: Goods)
        fun onRemove(goods: Goods)
    }
}

class GoodsInCartDiffCallback : DiffUtil.ItemCallback<Goods>() {
    override fun areItemsTheSame(oldItem: Goods, newItem: Goods): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Goods, newItem: Goods): Boolean {
        return oldItem.name == newItem.name
                && oldItem.price == newItem.price
                && oldItem.count == newItem.count
    }
}