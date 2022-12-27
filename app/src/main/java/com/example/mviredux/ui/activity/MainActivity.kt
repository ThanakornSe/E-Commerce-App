package com.example.mviredux.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import coil.load
import com.airbnb.epoxy.Carousel
import com.androidfactory.fakestore.model.domain.Product
import com.androidfactory.fakestore.model.mapper.ProductMapper
import com.example.mviredux.R
import com.example.mviredux.ui.adapter.controller.ProductEpoxyController
import com.example.mviredux.databinding.ActivityMainBinding
import com.example.mviredux.model.network.NetworkProduct
import com.example.mviredux.model.ui.UiProduct
import com.example.mviredux.network.ProductsServices
import com.example.mviredux.redux.ApplicationState
import com.example.mviredux.redux.Store
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

    @Inject
    lateinit var store: Store<ApplicationState>
    //store is singleton so it will be same store

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //make 2 fragment of bottom bar as Top Level fragment with no navigation up button
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.productListFragment,
                R.id.profileFragment,
                R.id.cartFragment
            )
        )
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        setupActionBarWithNavController(navController, appBarConfiguration)

        //Bottom Navigation Setup
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        //Prevent epoxy Carousel snapping
        Carousel.setDefaultGlobalSnapHelperFactory(null)

        //Add Badge on Bottom Navigation menu
        store.stateFlow.map { it.inCartProductIds.size }.distinctUntilChanged().asLiveData()
            .observe(this) { numberOfProductsInCart ->
                binding.bottomNavigationView.getOrCreateBadge(R.id.cartFragment).apply {
                    isVisible = numberOfProductsInCart > 0
                    number = numberOfProductsInCart
                }
            }
    }

}