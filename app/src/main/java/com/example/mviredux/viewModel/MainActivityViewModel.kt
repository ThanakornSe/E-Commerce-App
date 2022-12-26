package com.example.mviredux.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidfactory.fakestore.model.domain.Product
import com.example.mviredux.redux.ApplicationState
import com.example.mviredux.redux.Store
import com.example.mviredux.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: ProductRepository,
    val store: Store<ApplicationState>
) : ViewModel() {

    fun fetchProducts() = viewModelScope.launch {
        val products:List<Product> = repository.fetchAllProducts()
        store.update {
            return@update it.copy(products = products)
            /*
            ApplicationState is DataClass that's mean we can have hashcode and copy
            then we can specify only we want to change
            so we use .copy and just change stuff that we care about (products)
            and the last we return T (ApplicationState) that hold lastest list of Products
             */
        }
    }

}