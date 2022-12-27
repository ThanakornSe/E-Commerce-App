package com.example.mviredux.model.ui

import com.example.mviredux.model.domain.Filter

data class UiFilter(
    val filter: Filter,
    val isSelected: Boolean
)
