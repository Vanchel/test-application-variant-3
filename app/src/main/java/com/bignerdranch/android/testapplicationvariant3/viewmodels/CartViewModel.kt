package com.bignerdranch.android.testapplicationvariant3.viewmodels

import androidx.lifecycle.*
import com.bignerdranch.android.testapplicationvariant3.domain.Goods
import com.bignerdranch.android.testapplicationvariant3.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val testRepository: TestRepository) : ViewModel() {
    val goodsInCart = testRepository.goodsInCart.asLiveData()

    val totalCost: LiveData<Double> = Transformations.map(goodsInCart) {
        var total = 0.0
        goodsInCart.value?.let {
            for (goods in it) {
                total += goods.price * goods.count
            }
        }
        total
    }

    fun add(goods: Goods) = viewModelScope.launch {
        testRepository.updateGoods(goods.copy(count = goods.count + 1))
    }

    fun remove(goods: Goods) = viewModelScope.launch {
        testRepository.updateGoods(goods.copy(count = goods.count - 1))
    }
}