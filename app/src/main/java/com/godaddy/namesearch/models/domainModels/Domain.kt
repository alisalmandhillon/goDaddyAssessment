package com.godaddy.namesearch.models.domainModels

data class Domain(
    val name: String,
    val price: String,
    val productId: Int,
    var selected: Boolean = false
)