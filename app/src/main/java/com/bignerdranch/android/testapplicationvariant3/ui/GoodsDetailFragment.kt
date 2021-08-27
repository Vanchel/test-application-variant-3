package com.bignerdranch.android.testapplicationvariant3.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bignerdranch.android.testapplicationvariant3.databinding.FragmentGoodsDetailBinding
import com.bignerdranch.android.testapplicationvariant3.viewmodels.GoodsDetailViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val ARG_GOODS_ID = "goodsId"

@AndroidEntryPoint
class GoodsDetailFragment : Fragment() {
    @Inject
    lateinit var goodsDetailViewModelFactory: GoodsDetailViewModel.Factory
    private val viewModel: GoodsDetailViewModel by viewModels {
        GoodsDetailViewModel.provideFactory(
            goodsDetailViewModelFactory,
            arguments?.getString(ARG_GOODS_ID)!!
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGoodsDetailBinding.inflate(inflater, container, false)

        viewModel.goods.observe(viewLifecycleOwner) {
            binding.goods = it
            Glide.with(binding.root).load(it.imageUrl).into(binding.image)
            binding.executePendingBindings()
        }

        binding.cartButton.setOnClickListener { viewModel.updateGoods() }

        return binding.root
    }

    companion object {
        fun newInstance(goodsId: String) = GoodsDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_GOODS_ID, goodsId)
            }
        }
    }
}