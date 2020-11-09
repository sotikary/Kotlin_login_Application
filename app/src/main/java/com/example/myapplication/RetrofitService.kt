package com.example.myapplication

import android.media.tv.TvContract.Channels.CONTENT_TYPE
import android.util.Base64
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class RetrofitService {
    private val API_URL = "https://reqres.in/api"
    private var retrofit: Retrofit? = null
    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header(CONTENT_TYPE, "application/json")
                .addHeader(
                    "Authorization",
                    "Basic Auth" + Base64.encodeToString(
                        "eve.holt@reqres.in:cityslicka".toByteArray(),
                        Base64.NO_WRAP
                    )
                )
                .method(original.method, original.body);

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    fun getRetrofitClientService(): Retrofit {
        if (retrofit === null) {
             retrofit = Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            //return retrofit.create(APIService::class.java);

        }
        return this!!.retrofit!!
    }
}