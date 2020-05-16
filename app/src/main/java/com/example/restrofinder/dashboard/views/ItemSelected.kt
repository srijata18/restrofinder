package com.example.restrofinder.dashboard.views

import com.example.restrofinder.dashboard.dataModel.RestroModel
import com.example.restrofinder.dashboard.dataModel.Venues

interface ItemSelected {
    fun onItemSelected(isThumbsDown : Boolean, variantName : Venues?)
}