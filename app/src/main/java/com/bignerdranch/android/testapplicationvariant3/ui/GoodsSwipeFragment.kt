package com.bignerdranch.android.testapplicationvariant3.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.bignerdranch.android.testapplicationvariant3.R
import com.bignerdranch.android.testapplicationvariant3.adapters.GoodsSwipeAdapter
import com.bignerdranch.android.testapplicationvariant3.domain.Goods
import com.bignerdranch.android.testapplicationvariant3.viewmodels.GoodsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoodsSwipeFragment : Fragment() {
    private val viewModel: GoodsViewModel by hiltNavGraphViewModels(R.id.goods)
    private val args: GoodsSwipeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_goods_swipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val ids = viewModel.sortedGoods.value!!.map(Goods::id)
        val adapter = GoodsSwipeAdapter(this, ids)
        val viewPager: ViewPager2 = view.findViewById(R.id.pager)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(args.selectedIndex, false)
    }
}