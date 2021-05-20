package com.godaddy.namesearch.models.domainModels

import com.google.gson.annotations.SerializedName

data class DomainSearchRecommendedResponse(
    @SerializedName("Products") val products: List<DomainSearchProductResponse>,
    @SerializedName("RecommendedDomains") val domains: List<RecommendedDomain>
)