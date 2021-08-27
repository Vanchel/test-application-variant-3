package com.bignerdranch.android.testapplicationvariant3.viewmodels

import androidx.lifecycle.*
import com.bignerdranch.android.testapplicationvariant3.database.getDatabase
import com.bignerdranch.android.testapplicationvariant3.domain.Goods
import com.bignerdranch.android.testapplicationvariant3.network.NetworkStatus
import com.bignerdranch.android.testapplicationvariant3.repository.GoodsRepository
import com.bignerdranch.android.testapplicationvariant3.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class GoodsViewModel @Inject constructor(private val testRepository: TestRepository) : ViewModel() {
    private val goods = testRepository.goods.asLiveData()

    private val sortingType = MutableLiveData(SortingType.DEFAULT)

    val sortedGoods = Transformations.map(goods) {
        sortedGoods()
    }

    private val _status = MutableLiveData<NetworkStatus>()

    val shouldDisplayLoading: LiveData<Boolean> = Transformations.map(_status) {
        _status.value == NetworkStatus.LOADING && goods.value.isNullOrEmpty()
    }

    val shouldDisplayError: LiveData<Boolean> = Transformations.map(_status) {
        _status.value == NetworkStatus.ERROR && goods.value.isNullOrEmpty()
    }

    val shouldDisplayCacheHint: LiveData<Boolean> = Transformations.map(_status) {
        _status.value == NetworkStatus.ERROR && !goods.value.isNullOrEmpty()
    }

    init {
        refresh()
    }

    fun sortedGoods(): List<Goods> {
        goods.value?.let {
            return when (sortingType.value) {
                SortingType.PRICE -> it.sortedBy { it.price }
                SortingType.WEIGHT -> it.sortedByDescending { it.weight }
                else -> it
            }
        }
        return emptyList()
    }

    fun setSorting(sorting: SortingType) {
        sortingType.value = sorting
    }

    fun addToCart(goods: Goods) = viewModelScope.launch {
        testRepository.updateGoods(goods.copy(count = 1))
    }

    fun removeFromCart(goods: Goods) = viewModelScope.launch {
        testRepository.updateGoods(goods.copy(count = 0))
    }

    fun refresh() = viewModelScope.launch {
        _status.value = NetworkStatus.LOADING
        try {
            testRepository.refreshGoods()
            _status.value = NetworkStatus.DONE
        } catch (e: Exception) {
            _status.value = NetworkStatus.ERROR
        }
    }
}

enum class SortingType { DEFAULT, PRICE, WEIGHT }