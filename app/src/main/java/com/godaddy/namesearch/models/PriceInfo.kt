package com.godaddy.namesearch.models

import com.google.gson.annotations.SerializedName

data class PriceInfo(
    @SerializedName("CurrentPriceDisplay") val currentPriceDisplay: String,
    @SerializedName("ListPriceDisplay") val listPriceDisplay: String,
    @SerializedName("PromoRegLengthFlag") val promoLength: Int,
)