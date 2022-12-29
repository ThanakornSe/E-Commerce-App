package com.example.mviredux.ui.activity

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.airbnb.epoxy.Carousel
import com.example.mviredux.R
import com.example.mviredux.databinding.ActivityMainBinding
import com.example.mviredux.redux.ApplicationState
import com.example.mviredux.redux.Store
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
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

    fun navigateTab(@IdRes destinationId:Int){
        binding.bottomNavigationView.selectedItemId = destinationId
    }

}