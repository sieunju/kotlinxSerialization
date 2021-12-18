package com.hmju.sample.model

import com.hmju.sample.qualifiers.NullToEmptyString
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MoshiEntity(
    @NullToEmptyString
    val status: String = "Moshi Def",
    val data : MoshiData = MoshiData()
)

@JsonClass(generateAdapter = true)
data class MoshiData(
    val id : Long = 1000,
    val list : List<String> = listOf()
)