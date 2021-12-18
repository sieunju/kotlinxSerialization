package com.hmju.sample.model

import com.google.gson.annotations.SerializedName

data class GsonEntity(
    @SerializedName("status")
    val status: String = "Gson Def",
    @SerializedName("data")
    val data: GsonData = GsonData()
)

data class GsonData(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("list")
    val list: List<String> = listOf()
)