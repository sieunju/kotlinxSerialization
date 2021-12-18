package com.hmju.sample.model

import kotlinx.serialization.Serializable

@Serializable
data class KotlinxEntity(
    val status : String = "kotlinx Def",
    val data : KotlinxData = KotlinxData()
)

@Serializable
data class KotlinxData(
    val id : Long = -1L,
    val list : List<String> = listOf()
)