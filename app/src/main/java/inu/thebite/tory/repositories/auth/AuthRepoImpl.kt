package inu.thebite.tory.repositories.auth

import inu.thebite.tory.model.member.AddDirectorRequest
import inu.thebite.tory.model.member.AddTherapistRequest
import inu.thebite.tory.model.member.LoginResponse
import inu.thebite.tory.model.member.MemberLoginRequest
import inu.thebite.tory.model.member.ValidationTokenResponse
import inu.thebite.tory.retrofit.RetrofitApi
import retrofit2.Response

class AuthRepoImpl : AuthRepo {
    private val authRetrofit = RetrofitApi.apiService

    override suspend fun login(memberLoginRequest: MemberLoginRequest): Response<LoginResponse> {
        return authRetrofit.login(memberLoginRequest = memberLoginRequest)
    }

    override suspend fun signUpCenterDirector(addDirectorRequest: AddDirectorRequest): Response<Void> {
        return authRetrofit.joinPrincipalUser(addDirectorRequest = addDirectorRequest)
    }

    override suspend fun signUpCenterTeacher(addTherapistRequest: AddTherapistRequest): Response<Void> {
        return authRetrofit.joinTherapistUser(addTherapistRequest = addTherapistRequest)
    }

    override suspend fun validationToken(): Response<ValidationTokenResponse> {
        return authRetrofit.validationToken()
    }
}