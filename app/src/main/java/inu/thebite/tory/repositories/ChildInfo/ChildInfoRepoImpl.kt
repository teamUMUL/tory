package inu.thebite.tory.repositories.ChildInfo

import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.model.student.AddStudentRequest
import inu.thebite.tory.model.student.StudentResponse
import inu.thebite.tory.model.student.UpdateStudentDateRequest
import inu.thebite.tory.model.student.UpdateStudentRequest
import inu.thebite.tory.retrofit.RetrofitApi
import kotlinx.coroutines.flow.Flow

class ChildInfoRepoImpl : ChildInfoRepo {

    private val childRetrofit = RetrofitApi.apiService

    override suspend fun createChildInfo(
        childClass: ChildClassResponse,
        childInfo: AddStudentRequest
    ) {
        childRetrofit.addStudent(
            classId = childClass.id,
            addStudentRequest = childInfo)
    }

    override suspend fun getAllChildInfos(): List<StudentResponse> {
        return childRetrofit.getStudentList()
    }

    override suspend fun updateStudent(
        childInfo: StudentResponse,
        updateStudentRequest: UpdateStudentRequest
    ) {
        childRetrofit.updateStudent(studentId = childInfo.id, updateStudentRequest = updateStudentRequest)
    }

    override suspend fun updateStudentStartDate(
        childInfo: StudentResponse,
        studentDateRequest: UpdateStudentDateRequest
    ) {
        childRetrofit.updateStudentStartDate(
            studentId = childInfo.id,
            updateStudentDateRequest = studentDateRequest
        )
    }

    override suspend fun updateStudentEndDate(
        childInfo: StudentResponse,
        studentDateRequest: UpdateStudentDateRequest
    ) {
        childRetrofit.updateStudentEndDate(
            studentId = childInfo.id,
            updateStudentDateRequest = studentDateRequest
        )
    }

    override suspend fun deleteChildInfo(childInfo: StudentResponse) {
        childRetrofit.deleteStudent(studentId = childInfo.id)
    }
}