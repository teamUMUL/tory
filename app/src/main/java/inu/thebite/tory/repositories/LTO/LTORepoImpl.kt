package inu.thebite.tory.repositories.LTO


import inu.thebite.tory.model.lto.AddLtoRequest
import inu.thebite.tory.model.lto.LtoResponse
import inu.thebite.tory.model.student.StudentResponse
import kotlinx.coroutines.flow.Flow

class LTORepoImpl: LTORepo {
    override suspend fun createLTO(lto: AddLtoRequest) {
    }

    override suspend fun getAllLTOs(): List<LtoResponse> {
        return emptyList()
    }

//    override suspend fun updateLTO(updatedLTO: LTOEntity) {
//        ltoDao.updateLTO(updatedLTO)
//    }
//
//    override suspend fun deleteLTO(lto: LTOEntity) {
//        ltoDao.deleteLTO(lto)
//    }
}