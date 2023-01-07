package com.snowdango.violet.repository.api.provide

import com.snowdango.violet.repository.api.provide.endpoint.AppleMusicApi
import com.snowdango.violet.repository.api.provide.endpoint.SongLinkApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiProvider {

    val songLinkApi: SongLinkApi by lazy {
        provideApi<SongLinkApi>("https://api.song.link/v1-alpha.1/")
    }
    val appleMusicApi: AppleMusicApi by lazy {
        provideApi<AppleMusicApi>("https://itunes.apple.com/")
    }

    private inline fun <reified T> provideApi(
        baseUrl: String
    ) = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(provideOkhttpClient(provideHttpLoggingInterceptor()))
        .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
        .build()
        .create(T::class.java)

    private fun provideMoshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private fun provideOkhttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
        return builder.build()
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

}