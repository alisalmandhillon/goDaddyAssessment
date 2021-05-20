package com.godaddy.namesearch.models.domainModels

import com.godaddy.namesearch.models.Content
import com.godaddy.namesearch.models.PriceInfo
import com.google.gson.annotations.SerializedName

data class DomainSearchProductResponse(
    @SerializedName("PriceInfo") val priceInfo: PriceInfo,
    @SerializedName("Content") val content: Content?,
    @SerializedName("ProductId") val productId: Int
)