package com.example.mviredux.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidfactory.fakestore.model.domain.Product
import com.example.mviredux.model.domain.Filter
import com.example.mviredux.redux.ApplicationState
import com.example.mviredux.redux.Store
import com.example.mviredux.redux.reducer.FilterGenerator
import com.example.mviredux.redux.reducer.UIProductListReducer
import com.example.mviredux.redux.updater.UiProductFavoriteUpdater
import com.example.mviredux.redux.updater.UiProductInCartUpdater
import com.example.mviredux.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: ProductRepository,
    val store: Store<ApplicationState>,
    private val filterGenerator: FilterGenerator,
    val uiProductListReducer: UIProductListReducer,
    val uiProductFavoriteUpdater: UiProductFavoriteUpdater,
    val uiProductInCartUpdater: UiProductInCartUpdater
) : ViewModel() {

    fun fetchProducts() = viewModelScope.launch {
        if(store.read { it.products }.isNotEmpty()) return@launch

        val products: List<Product> = repository.fetchAllProducts()

        val filter: Set<Filter> = filterGenerator.generateFrom(products)

        store.update {
            return@update it.copy(
                products = products, productFilterInfo = ApplicationState.ProductFilterInfo(
                    filters = filter,
                    selectedFilter = it.productFilterInfo.selectedFilter
                )
            )
            /*
            ApplicationState is DataClass that's mean we can have hashcode and copy
            then we can specify only we want to change
            so we use .copy and just change stuff that we care about (products)
            and the last we return T (ApplicationState) that hold lastest list of Products
             */
        }
    }

}

//val oldFilter = products.map { p ->
//    Filter(value = p.category, displayText = p.category)
//}.toSet()