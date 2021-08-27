package com.bignerdranch.android.testapplicationvariant3.viewmodels

import androidx.lifecycle.*
import com.bignerdranch.android.testapplicationvariant3.repository.TestRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class GoodsDetailViewModel @AssistedInject constructor(
    private val testRepository: TestRepository,
    @Assisted goodsId: String
) : ViewModel() {
    val goods = testRepository.getGoodsById(goodsId).asLiveData()

    fun updateGoods() = goods.value?.let {
        viewModelScope.launch {
            val newGoods = it.copy(count = if (it.count > 0) 0 else 1)
            testRepository.updateGoods(newGoods)
        }
    }

    companion object {
        fun provideFactory(assistedFactory: Factory, goodsId: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    if (modelClass.isAssignableFrom(GoodsDetailViewModel::class.java)) {
                        return assistedFactory.create(goodsId) as T
                    }
                    throw IllegalArgumentException("Unable to construct viewModel")
                }
            }
    }

    @AssistedFactory
    interface Factory {
        fun create(goodsId: String): GoodsDetailViewModel
    }
}