package com.bignerdranch.android.testapplicationvariant3.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bignerdranch.android.testapplicationvariant3.ui.GoodsDetailFragment

class GoodsSwipeAdapter(fragment: Fragment, private val ids: List<String>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount() = ids.size

    override fun createFragment(position: Int): Fragment {
        return GoodsDetailFragment.newInstance(ids[position])
    }
}