package com.godaddy.namesearch.models

import com.google.gson.annotations.SerializedName

data class ExactMatch (
    @SerializedName("Fqdn") val fqdn: String,
    @SerializedName("Extension") val tld: String,
    val tierId: Int,
    val isAvailable: Boolean,
    @SerializedName("IsPremiumTier") val isPremium: Boolean,
    @SerializedName("ProductId") val productId: Int
)