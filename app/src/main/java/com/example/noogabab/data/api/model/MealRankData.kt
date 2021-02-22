package com.example.noogabab.data.api.model


import com.google.gson.annotations.SerializedName

data class MealRankData(
    @SerializedName("cnt")
    val cnt: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("role")
    val role: String?
)