package com.example.mviredux.home.cart

import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.EpoxyTouchHelper
import com.example.mviredux.R
import com.example.mviredux.databinding.FragmentCartBinding
import com.example.mviredux.model.ui.CartFragmentUiState
import com.example.mviredux.model.ui.UiProductInCart
import com.example.mviredux.MainActivity
import com.example.mviredux.utils.AppConst
import com.example.mviredux.viewModel.CartFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.math.BigDecimal
import kotlin.math.max

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartFragmentViewModel by viewModels()
    private val epoxyController: CartFragmentEpoxyController by lazy {
        CartFragmentEpoxyController(
            ::onEmptyCardClick,
            ::onFavoriteClick,
            ::onDeleteClicked,
            ::onQuantityChange
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCartBinding.bind(view)

        binding.epoxyRecyclerView.setController(epoxyController)

        val uiProductInCartFlow = viewModel.uiProductListReducer.reduce(viewModel.store).map { uiProducts ->
            uiProducts.filter { it.isInCart }
        }

        combine(
            uiProductInCartFlow,
            viewModel.store.stateFlow.map { it.cartQuantitiesMap }
        ) { uiProductList , quantityMap ->
            uiProductList.map {
                UiProductInCart(uiProduct = it, quantity = quantityMap[it.product.id] ?: 1)
            }
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { inCartUiProduct ->
            val viewState = if (inCartUiProduct.isEmpty()) {
                CartFragmentUiState.Empty
            } else {
                CartFragmentUiState.NonEmpty(inCartUiProduct)
            }
            epoxyController.setData(viewState)
            updateTotalLayout(inCartUiProduct)
        }

        swipeToDelete()

        binding.checkoutButton.setOnClickListener {
            // todo
        }

    }

    private fun updateTotalLayout(inCartUiProduct:List<UiProductInCart>) {
        val totalAmount: BigDecimal = inCartUiProduct.sumOf { BigDecimal(it.quantity) * it.uiProduct.product.price }
        val description = "${inCartUiProduct.size} items for ${AppConst.currencyFormatter.format(totalAmount)}"
        binding.totalDescription.text = description
        binding.checkoutButton.isEnabled = inCartUiProduct.isNotEmpty()
    }

    private fun swipeToDelete() {
        EpoxyTouchHelper
            .initSwiping(binding.epoxyRecyclerView)
            .right()
            .withTarget(CartItemEpoxyModel::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<CartItemEpoxyModel>() {
                override fun onSwipeCompleted(
                    model: CartItemEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    model?.let { epoxyModel ->
                        onDeleteClicked(epoxyModel.uiProductInCart.uiProduct.product.id)
                    }
                }

                override fun onSwipeProgressChanged(
                    model: CartItemEpoxyModel?,
                    itemView: View?,
                    swipeProgress: Float,
                    canvas: Canvas?
                ) {
                    itemView?.findViewById<View>(R.id.swipeToDismissTextView)?.apply {
                        translationX = max(-itemView.translationX, -measuredWidth.toFloat())
                        alpha = 5f * swipeProgress //this is for start fading
                    }
    //                    itemView?.findViewById<View>(R.id.cl_parent)?.apply {
    //                        alpha = -(3f * swipeProgress)
    //                    }
                }
            })
    }

    private fun onEmptyCardClick() {
        (activity as? MainActivity)?.navigateTab(R.id.productListFragment)
    }

    private fun onFavoriteClick(productId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update {
                return@update viewModel.uiProductFavoriteUpdater.onProductFavoriteUpdate(
                    productId = productId, currentState = it
                )
            }
        }
    }

    private fun onDeleteClicked(productId: Int) {
        viewModel.viewModelScope.launch {
            viewModel.store.update {
                return@update viewModel.uiProductInCartUpdater.onProductInCartUpdate(
                    productId = productId, currentState = it
                )
            }
        }
    }

    private fun onQuantityChange(productId:Int ,quantity:Int) {
        if (quantity < 1) return
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val newMapEntry: Pair<Int, Int> = productId to quantity
                val newMap: Map<Int, Int> = currentState.cartQuantitiesMap + newMapEntry
                return@update currentState.copy(cartQuantitiesMap = newMap)
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}