package inu.thebite.tory.repositories.ChildInfo

import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.model.student.AddStudentRequest
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.model.student.UpdateStudentDateRequest
import inu.thebite.tory.model.student.UpdateStudentRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ChildInfoRepo {
    suspend fun createChildInfo(childClass: ChildClassResponse, childInfo: AddStudentRequest) : Response<StudentResponse>

    suspend fun getAllChildInfos(): List<StudentResponse>

    suspend fun updateStudent(childInfo: StudentResponse, updateStudentRequest: UpdateStudentRequest): Response<StudentResponse>

    suspend fun updateStudentStartDate(childInfo: StudentResponse, studentDateRequest: UpdateStudentDateRequest)

    suspend fun updateStudentEndDate(childInfo: StudentResponse, studentDateRequest: UpdateStudentDateRequest)

    suspend fun deleteChildInfo(childInfo: StudentResponse)
}