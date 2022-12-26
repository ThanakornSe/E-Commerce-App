package com.example.mviredux.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import coil.load
import com.androidfactory.fakestore.model.domain.Product
import com.androidfactory.fakestore.model.mapper.ProductMapper
import com.example.mviredux.R
import com.example.mviredux.adapter.controller.ProductEpoxyController
import com.example.mviredux.databinding.ActivityMainBinding
import com.example.mviredux.model.network.NetworkProduct
import com.example.mviredux.model.ui.UiProduct
import com.example.mviredux.network.ProductsServices
import com.example.mviredux.viewModel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val epoxyController: ProductEpoxyController by lazy {
        ProductEpoxyController(::onFavoriteIconClicked)
    }

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.epoxyRecyclerView.setController(epoxyController)
        epoxyController.setData(emptyList())

        combine(
            viewModel.store.stateFlow.map { it.products },
            viewModel.store.stateFlow.map { it.favoriteProductIds }
        ) { listOfProducts, setOfFavoriteIds ->
            listOfProducts.map { product ->
                UiProduct(product = product, isFavorite = setOfFavoriteIds.contains(product.id))
            }
        }.distinctUntilChanged().asLiveData().observe(this) { uiProduct ->
            epoxyController.setData(uiProduct)
        }

        viewModel.fetchProducts()

    }

    private fun onFavoriteIconClicked(productId:Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentFavIds = currentState.favoriteProductIds
                val newFavoriteIds: Set<Int> = if (currentFavIds.contains(productId)) {
                    currentFavIds.filter { it != productId }.toSet()
                    //this scope mean user deselected favorite
                }else {
                    //this scope mean user select favorite
                    currentFavIds + setOf(productId)
                }
                return@update currentState.copy(favoriteProductIds = newFavoriteIds)
            }
        }
    }

//    private fun setupListeners() {
//        binding.cardView.setOnClickListener {
//            binding.productDescriptionTextView.apply {
//                isVisible = !isVisible
//            }
//        }
//
//        binding.addToCartButton.setOnClickListener {
//            binding.inCartView.apply {
//                isVisible = !isVisible
//            }
//        }
//
//        var isFavorite = false
//        binding.favoriteImageView.setOnClickListener {
//            val imageRes: Int = if (isFavorite) {
//                R.drawable.ic_round_favorite_border_24
//            } else {
//                R.drawable.ic_round_favorite_24
//            }
//            binding.favoriteImageView.setIconResource(imageRes)
//            isFavorite = !isFavorite
//        }
//    }
}