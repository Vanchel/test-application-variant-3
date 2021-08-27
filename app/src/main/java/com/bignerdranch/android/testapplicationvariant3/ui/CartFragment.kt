package com.bignerdranch.android.testapplicationvariant3.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.bignerdranch.android.testapplicationvariant3.adapters.GoodsInCartAdapter
import com.bignerdranch.android.testapplicationvariant3.databinding.FragmentCartBinding
import com.bignerdranch.android.testapplicationvariant3.domain.Goods
import com.bignerdranch.android.testapplicationvariant3.viewmodels.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {
    private val viewModel: CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCartBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val countCallback = object : GoodsInCartAdapter.CountCallback {
            override fun onAdd(goods: Goods) { viewModel.add(goods) }

            override fun onRemove(goods: Goods) { viewModel.remove(goods) }
        }

        val adapter = GoodsInCartAdapter(countCallback)
        binding.recyclerView.adapter = adapter
        viewModel.goodsInCart.observe(viewLifecycleOwner, adapter::submitList)

        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(decoration)

        return binding.root
    }
}