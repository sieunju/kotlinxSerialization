package com.hmju.sample

import com.hmju.sample.model.GsonEntity
import com.hmju.sample.model.KotlinxEntity
import com.hmju.sample.model.MoshiEntity
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ApiService {
    @GET("/api/sample")
    fun fetchGsonSample(): Single<GsonEntity>

    @GET("/api/sample")
    fun fetchMoshiSample(): Single<MoshiEntity>

    @GET("/api/sample")
    fun fetchKotlinxSample(): Single<KotlinxEntity>
}