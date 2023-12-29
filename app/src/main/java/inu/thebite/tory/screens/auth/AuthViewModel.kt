package inu.thebite.tory.screens.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthViewModel(private val tokenManager: TokenManager) : ViewModel() {

    private val _loginState = MutableStateFlow<Boolean>(false)
    val loginState = _loginState.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()

    suspend fun login(
        context: Context,
        id : String,
        password: String
    ){
        try {
            _isLoading.update { true }
            delay(1500)
            if (id == "test" && password == "1234"){
                _loginState.update { true }
                _isLoading.update { false }
                Toasty.success(context, "로그인에 성공했습니다", Toast.LENGTH_SHORT, true).show()
                tokenManager.saveToken("exampleToken")
            } else {
                _loginState.update { false }
                _isLoading.update { false }
                Toasty.error(context, "로그인에 실패했습니다", Toast.LENGTH_SHORT, true).show()
            }
        } catch (e : Exception){
            Log.e("failed to login", e.message.toString())
        }
    }

    suspend fun logout(

    ){
        _isLoading.update { true }
        delay(1500)
        _loginState.update { false }
        _isLoading.update { false }
        tokenManager.clearToken()
    }

    fun verifyToken(token : String){
        if (token == "exampleToken"){
            _loginState.update { true }
        }
    }
}

val LoginState = compositionLocalOf<AuthViewModel> { error("User State Context Not Found!")}