package com.bignerdranch.android.testapplicationvariant3.ui

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.bignerdranch.android.testapplicationvariant3.R
import com.bignerdranch.android.testapplicationvariant3.adapters.GoodsAdapter
import com.bignerdranch.android.testapplicationvariant3.databinding.FragmentGoodsBinding
import com.bignerdranch.android.testapplicationvariant3.domain.Goods
import com.bignerdranch.android.testapplicationvariant3.viewmodels.SortingType
import com.bignerdranch.android.testapplicationvariant3.viewmodels.GoodsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoodsFragment : Fragment() {
    private var _binding: FragmentGoodsBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel: GoodsViewModel by hiltNavGraphViewModels(R.id.goods)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoodsBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val cartCallback = object : GoodsAdapter.CartCallback {
            override fun onAddToCart(goods: Goods) {
                viewModel.addToCart(goods)
            }

            override fun onRemoveFromCart(goods: Goods) {
                viewModel.removeFromCart(goods)
            }
        }

        val adapter = GoodsAdapter(cartCallback) {
            val index = viewModel.sortedGoods.value!!.indexOf(it)
            findNavController().navigate(
                GoodsFragmentDirections.actionGoodsFragmentToGoodsSwipeFragment(index)
            )
        }
        binding.recyclerView.adapter = adapter
        viewModel.sortedGoods.observe(viewLifecycleOwner, adapter::submitList)

        viewModel.shouldDisplayCacheHint.observe(viewLifecycleOwner) {
            if (it) {
                Snackbar.make(binding.root, R.string.cache_hint, Snackbar.LENGTH_LONG)
                    .show()
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
            binding.swipeRefresh.isRefreshing = false
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.goods_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort -> {
                showSortingPopupMenu()
                true
            }
            else -> false
        }
    }

    private fun showSortingPopupMenu() {
        val view = requireActivity().findViewById<View>(R.id.sort)
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.sort_menu, menu)

            setOnMenuItemClickListener {
                viewModel.setSorting(
                    when (it.itemId) {
                        R.id.price -> SortingType.PRICE
                        R.id.weight -> SortingType.WEIGHT
                        else -> SortingType.DEFAULT
                    }
                )
                (binding.recyclerView.adapter as GoodsAdapter)
                    .submitList(viewModel.sortedGoods())
                true
            }
            show()
        }
    }
}