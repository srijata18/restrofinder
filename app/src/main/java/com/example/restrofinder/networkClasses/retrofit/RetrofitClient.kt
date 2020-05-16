package com.example.restrofinder.networkClasses.retrofit

import com.example.restrofinder.networkClasses.retrofit.RequestUrl.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {

        private const val TIME_OUT = 60L
        fun create(): RetrofitServiceAnnotator {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client1 = OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)

            val retrofitClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client1.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofitClient.create(RetrofitServiceAnnotator::class.java)
        }
    }
}