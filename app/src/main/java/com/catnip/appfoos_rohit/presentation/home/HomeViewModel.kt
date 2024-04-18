package com.catnip.appfood_rohit.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.catnip.appfood_rohit.data.repository.CategoryRepository
import com.catnip.appfood_rohit.data.repository.ProductRepository
import com.catnip.appfood_rohit.data.source.local.pref.UserPreference
import kotlinx.coroutines.Dispatchers

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: ProductRepository,
    private val userPreference: UserPreference
) : ViewModel() {
    private val _isUsingGridMode = MutableLiveData(userPreference.isUsingGridMode())
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    fun getListMode(): Int {
        return if (userPreference.isUsingGridMode()) 1 else 0
    }

    fun changeListMode() {
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
        userPreference.setUsingGridMode(!currentValue)
    }

    fun getMenu(categoryName: String? = null) =
        menuRepository.getProducts(categoryName).asLiveData(Dispatchers.IO)
    fun getCategory() = categoryRepository.getCategories().asLiveData(Dispatchers.IO)

}