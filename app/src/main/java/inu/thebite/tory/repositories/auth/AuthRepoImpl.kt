package inu.thebite.tory.repositories.auth

import inu.thebite.tory.model.member.AddDirectorRequest
import inu.thebite.tory.model.member.AddTherapistRequest
import inu.thebite.tory.model.member.EditProfileRequest
import inu.thebite.tory.model.member.FindMemberIdRequest
import inu.thebite.tory.model.member.FindMemberIdResponse
import inu.thebite.tory.model.member.FindMemberPasswordRequest
import inu.thebite.tory.model.member.LoginResponse
import inu.thebite.tory.model.member.MemberLoginRequest
import inu.thebite.tory.model.member.MemberResponse
import inu.thebite.tory.model.member.TemporaryPasswordResponse
import inu.thebite.tory.model.member.UpdatePasswordRequest
import inu.thebite.tory.model.member.ValidationTokenResponse
import inu.thebite.tory.retrofit.RetrofitApi
import retrofit2.Response

class AuthRepoImpl : AuthRepo {
    private val authRetrofit = RetrofitApi.apiService

    override suspend fun login(memberLoginRequest: MemberLoginRequest): Response<LoginResponse> {
        return authRetrofit.login(memberLoginRequest = memberLoginRequest)
    }

    override suspend fun signUpCenterDirector(addDirectorRequest: AddDirectorRequest): Response<Boolean> {
        return authRetrofit.joinPrincipalUser(addDirectorRequest = addDirectorRequest)
    }

    override suspend fun signUpCenterTeacher(addTherapistRequest: AddTherapistRequest): Response<Boolean> {
        return authRetrofit.joinTherapistUser(addTherapistRequest = addTherapistRequest)
    }

    override suspend fun validationToken(): Response<ValidationTokenResponse> {
        return authRetrofit.validationToken()
    }

    override suspend fun updatePassword(updatePasswordRequest: UpdatePasswordRequest): Response<Boolean> {
        return authRetrofit.updatePassword(updatePasswordRequest = updatePasswordRequest)
    }

    override suspend fun findMemberId(findMemberIdRequest: FindMemberIdRequest): Response<FindMemberIdResponse> {
        return authRetrofit.findMemberId(findMemberIdRequest = findMemberIdRequest)
    }

    override suspend fun findMemberPassword(findMemberPasswordRequest: FindMemberPasswordRequest): Response<TemporaryPasswordResponse> {
        return authRetrofit.findMemberPassword(findMemberPasswordRequest = findMemberPasswordRequest)
    }

    override suspend fun editProfile(editProfileRequest: EditProfileRequest): Response<MemberResponse> {
        return authRetrofit.editProfile(editProfileRequest = editProfileRequest)
    }

    override suspend fun getProfile(): Response<MemberResponse> {
        return authRetrofit.getProfile()
    }
}