package inu.thebite.tory.repositories.ChildInfo

import inu.thebite.tory.model.student.AddStudentRequest
import inu.thebite.tory.model.student.StudentResponse
import kotlinx.coroutines.flow.Flow

interface ChildInfoRepo {
    suspend fun createChildInfo(childInfo: AddStudentRequest)
    suspend fun getAllChildInfos(): List<StudentResponse>
//    suspend fun updateChildInfo(updatedChildInfo: StudentResponse)
    suspend fun deleteChildInfo(childInfo: StudentResponse)
}