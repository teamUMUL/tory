package inu.thebite.tory.repositories.auth

import inu.thebite.tory.model.member.AddDirectorRequest
import inu.thebite.tory.model.member.AddTherapistRequest
import inu.thebite.tory.model.member.LoginResponse
import inu.thebite.tory.model.member.MemberLoginRequest
import inu.thebite.tory.model.member.ValidationTokenResponse
import retrofit2.Response

interface AuthRepo {
    suspend fun login(memberLoginRequest: MemberLoginRequest) : Response<LoginResponse>

    suspend fun signUpCenterDirector(addDirectorRequest: AddDirectorRequest) : Response<Boolean>

    suspend fun signUpCenterTeacher(addTherapistRequest: AddTherapistRequest) : Response<Boolean>

    suspend fun validationToken() : Response<ValidationTokenResponse>
}