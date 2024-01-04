package inu.thebite.tory.screens.auth

import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // 헤더에 토큰 추가
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer ${tokenManager.getToken()}")
            .build()

        return chain.proceed(newRequest)
    }
}