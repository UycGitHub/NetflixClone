package com.umutyusufcinar.netflixclone.di

import com.umutyusufcinar.netflixclone.AppConstants
import com.umutyusufcinar.netflixclone.network.NetworkResponseAdapterFactory
import com.umutyusufcinar.netflixclone.network.TmdbApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun providesInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newUrl = chain.request().url
                .newBuilder()
                .addQueryParameter("api_key", AppConstants.TMDB_API_KEY)
                .build()
            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }
    }

    @Singleton
    @Provides
    fun loggingClient(authInterceptor: Interceptor):OkHttpClient{
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .addNetworkInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides fun providesRetrofitInstance(loggingClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(loggingClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun tmdbApi(retrofit: Retrofit): TmdbApi {
        return retrofit.create(TmdbApi::class.java)
    }
}