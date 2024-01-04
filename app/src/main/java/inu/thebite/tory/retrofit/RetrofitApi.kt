package inu.thebite.tory.retrofit

import android.content.Context
import inu.thebite.tory.screens.auth.TokenExpirationInterceptor
import inu.thebite.tory.screens.auth.TokenInterceptor
import inu.thebite.tory.screens.auth.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi : KoinComponent{

    private const val BASE_URL = "http://192.168.35.162:8081"

    private val tokenManager: TokenManager by inject()


    private val okHttpClient: OkHttpClient by lazy {
        val context: Context = get()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(TokenInterceptor(tokenManager))
            .addInterceptor(TokenExpirationInterceptor(context = context))
            .build()
    }


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    val apiService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }
}