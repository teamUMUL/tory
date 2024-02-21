package inu.thebite.tory.screens.auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.dmoral.toasty.Toasty
import inu.thebite.tory.model.member.AddDirectorRequest
import inu.thebite.tory.model.member.AddTherapistRequest
import inu.thebite.tory.model.member.MemberLoginRequest
import inu.thebite.tory.repositories.STO.STORepoImpl
import inu.thebite.tory.repositories.auth.AuthRepoImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(private val tokenManager: TokenManager) : ViewModel() {
    private val repo: AuthRepoImpl = AuthRepoImpl()

    private val _loginState = MutableStateFlow<Boolean>(false)
    val loginState = _loginState.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading = _isLoading.asStateFlow()


    private val _signUpLoading = MutableStateFlow<Boolean>(false)
    val signUpLoading = _signUpLoading.asStateFlow()

    private val _userName = MutableStateFlow<String?>(null)
    val userName = _userName.asStateFlow()

    suspend fun login(
        context: Context,
        id : String,
        password: String
    ){
        viewModelScope.launch {
            try {
                _isLoading.update { true }
                delay(1500)

                val response = repo.login(memberLoginRequest = MemberLoginRequest(id = id, password = password))

                if (response.isSuccessful) {
                    val userInfo = response.body() ?: throw Exception("로그인 정보가 비어있습니다.")
                    tokenManager.saveToken(userInfo.token)
                    _userName.update { userInfo.name }

                    _loginState.update { true }
                    _isLoading.update { false }
                    TokenExpirationEvent.expired.postValue(false)

                    Toasty.success(context, "로그인에 성공했습니다", Toast.LENGTH_SHORT, true).show()
                } else {
                    _loginState.update { false }
                    _isLoading.update { false }
                    Toasty.error(context, "로그인에 실패했습니다", Toast.LENGTH_SHORT, true).show()
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("login 실패: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("failed to login", e.message.toString())
            }
        }

    }

    fun signUpDirector(
        name: String,
        id: String,
        password: String,
        email: String,
        phone: String,
        identity: String
    ){
        viewModelScope.launch {
            try {
                _signUpLoading.update { true }
                delay(1500)

                val response = repo.signUpCenterDirector(addDirectorRequest = AddDirectorRequest(
                    name = name,
                    id = id,
                    password = password,
                    email = email,
                    phone = phone,
                ))
                Log.d("signUpResponse", response.toString())
                if (response.isSuccessful){
                    _signUpLoading.update { false }
                }

            } catch (e: Exception) {
                Log.e("failed to signUp", e.message.toString())
            } finally {
                _signUpLoading.update { false }
            }
        }
    }

    fun signUpTherapist(
        name: String,
        id: String,
        password: String,
        email: String,
        phone: String,
        identity: String,
        centerCode: Long
    ){
        viewModelScope.launch {
            try {
                _signUpLoading.update { true }
                delay(1500)

                val response = repo.signUpCenterTeacher(addTherapistRequest = AddTherapistRequest(
                    name = name,
                    id = id,
                    password = password,
                    email = email,
                    phone = phone,
                    centerId = centerCode
                )
                )
                if (response.isSuccessful){
                    _signUpLoading.update { false }
                }

            } catch (e: Exception) {
                Log.e("failed to signUp", e.message.toString())
            } finally {
                _signUpLoading.update { false }
            }
        }
    }

    fun logout(

    ){
        _isLoading.update { true }
        _loginState.update { false }
        _isLoading.update { false }
        TokenExpirationEvent.expired.postValue(true)
        tokenManager.clearToken()
    }

    fun verifyToken(context: Context){
        viewModelScope.launch {
            try {
                _isLoading.update { true }

                val response = repo.validationToken()

                if (response.isSuccessful) {
                    val userInfo = response.body() ?: throw Exception("유저 정보가 비어있습니다.")
                    if (userInfo.result){
                        _userName.update { userInfo.name }
                        Log.d("isValidated", userInfo.toString())
                        _loginState.update { true }
                        _isLoading.update { false }
                        Toasty.success(context, "로그인에 성공했습니다", Toast.LENGTH_SHORT, true).show()
                    }

                } else {
                    _loginState.update { false }
                    _isLoading.update { false }
                    Toasty.error(context, "토큰이 유효하지 않습니다", Toast.LENGTH_SHORT, true).show()
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("validation 실패: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("failed to login", e.message.toString())
            }
        }
    }
}

val LoginState = compositionLocalOf<AuthViewModel> { error("User State Context Not Found!")}