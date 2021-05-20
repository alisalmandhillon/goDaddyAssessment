package com.godaddy.namesearch.models.domainModels

import com.google.gson.annotations.SerializedName

data class RecommendedDomain(
    @SerializedName("Fqdn") val fqdn: String,
    @SerializedName("Extension") val tld: String,
    val tierId: Int,
    @SerializedName("IsPremiumTier") val isPremium: Boolean,
    @SerializedName("ProductId") val productId: Int,
    val inventory: String
)