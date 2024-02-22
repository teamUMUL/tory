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
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthRepo {
    suspend fun login(memberLoginRequest: MemberLoginRequest) : Response<LoginResponse>

    suspend fun signUpCenterDirector(addDirectorRequest: AddDirectorRequest) : Response<Boolean>

    suspend fun signUpCenterTeacher(addTherapistRequest: AddTherapistRequest) : Response<Boolean>

    suspend fun validationToken() : Response<ValidationTokenResponse>

    suspend fun updatePassword(updatePasswordRequest: UpdatePasswordRequest) : Response<Boolean>

    // 아이디 찾기
    suspend fun findMemberId(findMemberIdRequest: FindMemberIdRequest) : Response<FindMemberIdResponse>

    // 비밀번호 찾기
    suspend fun findMemberPassword(findMemberPasswordRequest: FindMemberPasswordRequest) : Response<TemporaryPasswordResponse>

    // 프로필 편집
    suspend fun editProfile(editProfileRequest: EditProfileRequest) : Response<MemberResponse>

    suspend fun getProfile() : Response<MemberResponse>

}