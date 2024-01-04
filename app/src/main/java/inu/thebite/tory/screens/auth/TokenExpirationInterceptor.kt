package inu.thebite.tory.screens.auth

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.MutableLiveData
import es.dmoral.toasty.Toasty
import okhttp3.Interceptor
import okhttp3.Response

class TokenExpirationInterceptor(
    private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        // 토큰 만료 감지 (예: HTTP 401 Unauthorized)
        if (response.code == 403) {
            // AuthViewModel을 통해 로그인 상태 업데이트
            TokenExpirationEvent.expired.postValue(true)
            Handler(Looper.getMainLooper()).post {
                Toasty.warning(context, "토큰이 만료되었습니다. 다시 로그인해주세요.", Toast.LENGTH_LONG).show()
            }
        }

        return response
    }
}

object TokenExpirationEvent {
    val expired = MutableLiveData<Boolean>(false)
}