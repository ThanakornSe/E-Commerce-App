package com.example.mviredux

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.mviredux.databinding.ActivityMainBinding
import com.example.mviredux.network.ProductsServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var productsServices: ProductsServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        refreshData()
        setupListeners()
    }

    private fun refreshData() {
        lifecycleScope.launchWhenStarted {
            binding.productImageViewLoadingProgressBar.isVisible = true
            val response = productsServices.getAllProducts()
            binding.productImageView.load(
                data = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"
            ) {
                listener { request, result ->
                    binding.productImageViewLoadingProgressBar.isGone = true
                }
            }
            Log.i("DATA", response.body()!!.toString())
        }
    }

    private fun setupListeners() {
        binding.cardView.setOnClickListener {
            binding.productDescriptionTextView.apply {
                isVisible = !isVisible
            }
        }

        binding.addToCartButton.setOnClickListener {
            binding.inCartView.apply {
                isVisible = !isVisible
            }
        }

        var isFavorite = false
        binding.favoriteImageView.setOnClickListener {
            val imageRes = if (isFavorite) {
                R.drawable.ic_round_favorite_border_24
            } else {
                R.drawable.ic_round_favorite_24
            }
            binding.favoriteImageView.setIconResource(imageRes)
            isFavorite = !isFavorite
        }
    }
}