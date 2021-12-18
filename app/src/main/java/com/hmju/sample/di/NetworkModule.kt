package com.hmju.sample.di

import com.hmju.sample.ApiService
import com.hmju.sample.qualifiers.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    @BaseUrl
    fun baseUrl(): String = "http://172.20.10.6:50057"

    @Provides
    fun provideApiClient() = OkHttpClient.Builder().apply {
        connectTimeout(5, TimeUnit.SECONDS)
        readTimeout(5, TimeUnit.SECONDS)
        writeTimeout(5, TimeUnit.SECONDS)
    }.build()

    @Provides
    @Singleton
    @GsonApiService
    fun provideGsonApiService(@BaseUrl baseUrl: String, httpClient: OkHttpClient): ApiService {
        return Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(httpClient)
            addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            addConverterFactory(GsonConverterFactory.create())
        }.build().create(ApiService::class.java)
    }

    class NullToEmptyStringAdapter {
        @ToJson
        fun toJson(@NullToEmptyString value: String?): String? {
            return value
        }

        @FromJson
        @NullToEmptyString
        fun fromJson(@javax.annotation.Nullable data: String?): String {
            return data ?: ""
        }
    }

    @Provides
    @Singleton
    @MoshiApiService
    fun provideMoshiApiService(@BaseUrl baseUrl: String, httpClient: OkHttpClient): ApiService {
        return Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(httpClient)
            addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(NullToEmptyStringAdapter())
                        .add(KotlinJsonAdapterFactory())
                        .build()
                ).asLenient().failOnUnknown().withNullSerialization()
            )
        }.build().create(ApiService::class.java)
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    @KotlinxApiService
    fun provideKotlinxApiService(@BaseUrl baseUrl: String, httpClient: OkHttpClient): ApiService {
        return Retrofit.Builder().apply {
            baseUrl(baseUrl)
            client(httpClient)
            addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
            addConverterFactory(Json {
                isLenient = true // Json 큰따옴표 느슨하게 체크.
                ignoreUnknownKeys = true // Field 값이 없는 경우 무시
                coerceInputValues = true // "null" 이 들어간경우 default Argument 값으로 대체
            }.asConverterFactory("application/json".toMediaType()))
        }.build().create(ApiService::class.java)
    }
}