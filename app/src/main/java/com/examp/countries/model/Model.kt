package com.examp.countries.model

import com.google.gson.annotations.SerializedName

data class Country(
        @SerializedName("name")
        val countryName: String?,
        @SerializedName("region")
        val countryRegion: String?,
        @SerializedName("capital")
        val countryCapital: String?,
        @SerializedName("currency")
        val countryCurrency: String?,
        @SerializedName("flag")
        val countryLanguage: String?,
        @SerializedName("language")
        val imageUrl: String?
)