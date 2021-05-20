package com.godaddy.namesearch.models.domainModels

import com.godaddy.namesearch.models.ExactMatch
import com.google.gson.annotations.SerializedName

data class DomainSearchExactMatchResponse (
    @SerializedName("Products") val products: List<DomainSearchProductResponse>,
    @SerializedName("ExactMatchDomain") val domain: ExactMatch
)