package com.example.mviredux.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import coil.load
import com.androidfactory.fakestore.model.domain.Product
import com.androidfactory.fakestore.model.mapper.ProductMapper
import com.example.mviredux.R
import com.example.mviredux.adapter.controller.ProductEpoxyController
import com.example.mviredux.databinding.ActivityMainBinding
import com.example.mviredux.model.network.NetworkProduct
import com.example.mviredux.network.ProductsServices
import com.example.mviredux.viewModel.MainActivityViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val epoxyController: ProductEpoxyController by lazy {
        ProductEpoxyController()
    }

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.epoxyRecyclerView.setController(epoxyController)
        epoxyController.setData(emptyList())

        viewModel.store.stateFlow.map {
            it.products
        }.distinctUntilChanged().asLiveData().observe(this) { products ->
            products?.let {
                epoxyController.setData(it)
            }
            if (products.isEmpty()) {
                Snackbar.make(binding.root, "Failed to fetch", Snackbar.LENGTH_LONG).show()
            }
        }
        viewModel.fetchProducts()

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