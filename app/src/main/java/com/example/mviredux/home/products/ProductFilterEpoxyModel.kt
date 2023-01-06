package com.example.mviredux.home.products

import androidx.core.content.ContextCompat
import com.example.mviredux.R
import com.example.mviredux.databinding.EpoxyModelProductFilterBinding
import com.example.mviredux.model.domain.Filter
import com.example.mviredux.model.ui.UiFilter
import com.example.mviredux.utils.ViewBindingKotlinModel

data class ProductFilterEpoxyModel(
    val uiFilter:UiFilter,
    val onFilterSelected: (Filter) -> Unit
):ViewBindingKotlinModel<EpoxyModelProductFilterBinding>(R.layout.epoxy_model_product_filter) {
    override fun EpoxyModelProductFilterBinding.bind() {
        root.setOnClickListener { onFilterSelected(uiFilter.filter) }
        filterNameTextView.text = uiFilter.filter.displayText

        val cardBackgroundColorResId = if (uiFilter.isSelected) {
            R.color.purple_500
        } else {
            R.color.purple_200
        }
        root.setCardBackgroundColor(ContextCompat.getColor(root.context, cardBackgroundColorResId))
    }

}
