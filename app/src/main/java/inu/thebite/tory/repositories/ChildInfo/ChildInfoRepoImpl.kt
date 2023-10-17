package inu.thebite.tory.repositories.ChildInfo

import inu.thebite.tory.model.student.AddStudentRequest
import inu.thebite.tory.model.student.StudentResponse
import kotlinx.coroutines.flow.Flow

class ChildInfoRepoImpl : ChildInfoRepo {


    override suspend fun createChildInfo(childInfo: AddStudentRequest) {
//        childInfoDao.insertChildInfo(childInfo)
    }

    override suspend fun getAllChildInfos(): List<StudentResponse> {
//        return childInfoDao.getAllChildInfos()
        return emptyList()
    }

//    override suspend fun updateChildInfo(updatedChildInfo: ChildInfoEntity) {
//        childInfoDao.updateChildInfo(updatedChildInfo)
//    }

    override suspend fun deleteChildInfo(childInfo: StudentResponse) {
//        childInfoDao.deleteChildInfo(childInfo)
    }
}