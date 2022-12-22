package com.example.mviredux.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.androidfactory.fakestore.model.domain.Product
import com.androidfactory.fakestore.model.mapper.ProductMapper
import com.example.mviredux.R
import com.example.mviredux.adapter.controller.ProductEpoxyController
import com.example.mviredux.databinding.ActivityMainBinding
import com.example.mviredux.network.ProductsServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val epoxyController:ProductEpoxyController by lazy {
        ProductEpoxyController()
    }

    @Inject
    lateinit var productsServices: ProductsServices
    @Inject
    lateinit var productMapper:ProductMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        refreshData()
        binding.epoxyRecyclerView.setController(epoxyController)
//        setupListeners()
    }

    private fun refreshData() {
        lifecycleScope.launchWhenStarted {
            val response = productsServices.getAllProducts()
            val domainProduct:List<Product> = response.body()!!.map {
                productMapper.buildFrom(networkProduct = it)
            }
            epoxyController.setData(domainProduct)
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