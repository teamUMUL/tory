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
import inu.thebite.tory.model.member.EditProfileRequest
import inu.thebite.tory.model.member.FindMemberIdRequest
import inu.thebite.tory.model.member.FindMemberIdResponse
import inu.thebite.tory.model.member.FindMemberPasswordRequest
import inu.thebite.tory.model.member.MemberLoginRequest
import inu.thebite.tory.model.member.MemberResponse
import inu.thebite.tory.model.member.TemporaryPasswordResponse
import inu.thebite.tory.model.member.UpdatePasswordRequest
import inu.thebite.tory.repositories.auth.AuthRepoImpl
import inu.thebite.tory.screens.education.compose.sidebar.currentToast
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

    private val _signUpSuccess = MutableStateFlow<Boolean?>(null)
    val signUpSuccess = _signUpSuccess.asStateFlow()

    private val _userName = MutableStateFlow<String?>(null)
    val userName = _userName.asStateFlow()

    private val _selectedQualifications = MutableStateFlow<List<String>?>(null)
    val selectedQualifications = _selectedQualifications.asStateFlow()

    private val _foundId = MutableStateFlow<FindMemberIdResponse?>(null)
    val foundId = _foundId.asStateFlow()

    private val _temporaryPassword = MutableStateFlow<TemporaryPasswordResponse?>(null)
    val temporaryPassword = _temporaryPassword.asStateFlow()

    private val _userInfo = MutableStateFlow<MemberResponse?>(null)
    val userInfo = _userInfo.asStateFlow()

    fun clearFoundId(){
        _foundId.update { null }
    }

    fun clearSignUpSuccess(){
        _signUpSuccess.update { null }
    }

    fun clearTemporaryPassword(){
        _temporaryPassword.update { null }
    }

    fun addSelectedQualification(qualification : String){
        _selectedQualifications.update {beforeQualifications ->
            beforeQualifications?.plus(qualification) ?: listOf(qualification)
        }
    }

    fun setSelectedQualification(qualifications : List<String>){
        _selectedQualifications.update {
            qualifications
        }
    }
    fun removeSelectedQualification(qualification : String){
        _selectedQualifications.update {beforeQualifications ->
            beforeQualifications?.filter { it != qualification }
        }
    }
    suspend fun login(
        context: Context,
        id: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                _isLoading.update { true }
                delay(1500)

                val response = repo.login(
                    memberLoginRequest = MemberLoginRequest(
                        id = id,
                        password = password
                    )
                )

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
        context : Context,
        name: String,
        id: String,
        password: String,
        email: String,
        phone: String,
        identity: String
    ) {
        viewModelScope.launch {
            try {
                _signUpLoading.update { true }
                delay(1500)

                val response = repo.signUpCenterDirector(
                    addDirectorRequest = AddDirectorRequest(
                        name = name,
                        id = id,
                        password = password,
                        email = email,
                        phone = phone,
                    )
                )
                Log.d("signUpResponse", response.toString())
                if (response.isSuccessful) {
                    val isSuccess = response.body() ?: throw Exception("유저 정보가 비어있습니다.")
                    if (isSuccess){
                        _signUpSuccess.update { true }
                        Toasty.success(context, "회원가입에 성공했습니다", Toast.LENGTH_SHORT, true).show()
                    } else {
                        _signUpSuccess.update { false }
                    }

                }

            } catch (e: Exception) {
                Log.e("failed to signUp", e.message.toString())
                _signUpSuccess.update { false }
            } finally {
                _signUpLoading.update { false }
            }
        }
    }

    fun signUpTherapist(
        context : Context,
        name: String,
        id: String,
        password: String,
        email: String,
        phone: String,
        identity: String,
        centerCode: Long
    ) : Boolean {
        viewModelScope.launch {
            try {
                _signUpLoading.update { true }
                delay(1500)

                val response = repo.signUpCenterTeacher(
                    addTherapistRequest = AddTherapistRequest(
                        name = name,
                        id = id,
                        password = password,
                        email = email,
                        phone = phone,
                        centerId = centerCode
                    )
                )
                Log.d("signUpResponse", response.toString())
                if (response.isSuccessful) {
                    val isSuccess = response.body() ?: throw Exception("유저 정보가 비어있습니다.")
                    if (isSuccess){
                        _signUpSuccess.update { true }
                        Toasty.success(context, "회원가입에 성공했습니다", Toast.LENGTH_SHORT, true).show()
                    } else {
                        _signUpSuccess.update { false }
                    }

                }

            } catch (e: Exception) {
                Log.e("failed to signUp", e.message.toString())
                _signUpSuccess.update { false }

            } finally {
                _signUpLoading.update { false }
            }
        }
        return false
    }

    fun logout(

    ) {
        _isLoading.update { true }
        _loginState.update { false }
        _isLoading.update { false }
        TokenExpirationEvent.expired.postValue(true)
        tokenManager.clearToken()
    }

    fun verifyToken(context: Context) {
        viewModelScope.launch {
            try {
                _isLoading.update { true }

                val response = repo.validationToken()

                if (response.isSuccessful) {
                    val userInfo = response.body() ?: throw Exception("유저 정보가 비어있습니다.")
                    if (userInfo.result) {
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

    fun updatePassword(context: Context, updatePasswordRequest: UpdatePasswordRequest) {
        viewModelScope.launch {
            try {

                val response = repo.updatePassword(updatePasswordRequest = updatePasswordRequest)

                if (response.isSuccessful) {
                    val isChanged = response.body() ?: throw Exception("비밀번호 변경 성공 유무가 비어있습니다.")
                    if (isChanged) {
                        Toasty.success(context, "비밀번호 변경에 성공하셨습니다.", Toast.LENGTH_SHORT, true).show()
                    } else {
                        Toasty.warning(context, "비밀번호 변경에 실패하셨습니다.", Toast.LENGTH_SHORT, true).show()
                    }

                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("비밀번호 변경 실패: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("failed to change password", e.message.toString())
            }
        }
    }

    fun findMemberId(context: Context,findMemberIdRequest: FindMemberIdRequest) {
        viewModelScope.launch {
            try {

                val response = repo.findMemberId(findMemberIdRequest = findMemberIdRequest)

                if (response.isSuccessful) {
                    val gottenId = response.body() ?: throw Exception("아이디 정보가 비어있습니다.")
                    _foundId.update { gottenId }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    currentToast?.cancel()
                    val newToast = Toasty.warning(context, "해당하는 정보의 유저가 존재하지 않습니다.", Toast.LENGTH_SHORT, true)
                    newToast.show()
                    currentToast = newToast
                    throw Exception("아이디 찾기 실패: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("failed to find Id", e.message.toString())
                currentToast?.cancel()
                val newToast = Toasty.warning(context, "해당하는 정보의 유저가 존재하지 않습니다.", Toast.LENGTH_SHORT, true)
                newToast.show()
                currentToast = newToast
            }
        }
    }

    fun findMemberPassword(context : Context, findMemberPasswordRequest: FindMemberPasswordRequest) {
        viewModelScope.launch {
            try {

                val response = repo.findMemberPassword(findMemberPasswordRequest = findMemberPasswordRequest)

                if (response.isSuccessful) {
                    val gottenTemporaryPassword = response.body() ?: throw Exception("임시 비밀번호 정보가 비어있습니다.")
                    _temporaryPassword.update { gottenTemporaryPassword }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    currentToast?.cancel()
                    val newToast = Toasty.warning(context, "해당하는 정보의 유저가 존재하지 않습니다.", Toast.LENGTH_SHORT, true)
                    newToast.show()
                    currentToast = newToast
                    throw Exception("임시 비밀번호 부여 실패: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("failed to get temporary password", e.message.toString())
                currentToast?.cancel()
                val newToast = Toasty.warning(context, "해당하는 정보의 유저가 존재하지 않습니다.", Toast.LENGTH_SHORT, true)
                newToast.show()
                currentToast = newToast
            }
        }
    }

    fun editProfile(editProfileRequest: EditProfileRequest) {
        viewModelScope.launch {
            try {

                val response = repo.editProfile(editProfileRequest = editProfileRequest)

                if (response.isSuccessful) {
                    val editedProfile = response.body() ?: throw Exception("프로필 수정 정보가 비어있습니다.")
                    _userInfo.update { editedProfile }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"

                    throw Exception("프로필 수정 실패: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("failed to edit profile", e.message.toString())
            }
        }
    }

    fun getProfile(){
        viewModelScope.launch {
            try {

                val response = repo.getProfile()

                if (response.isSuccessful) {
                    val gottenProfile = response.body() ?: throw Exception("프로필  정보가 비어있습니다.")
                    _userInfo.update { gottenProfile }
                } else {
                    val errorBody = response.errorBody()?.string() ?: "알 수 없는 에러 발생"
                    throw Exception("프로필 조회 실패: $errorBody")
                }
            } catch (e: Exception) {
                Log.e("failed to get profile", e.message.toString())
            }
        }
    }

}

val LoginState = compositionLocalOf<AuthViewModel> { error("User State Context Not Found!") }