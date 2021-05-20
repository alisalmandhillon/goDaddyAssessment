package com.godaddy.namesearch.models

import com.google.gson.annotations.SerializedName

data class Content (
    val header: String?,
    val messages: String?,
    val phases: List<String>?,
    val subHeader: String?,
    @SerializedName("TLD") val tld: String
)