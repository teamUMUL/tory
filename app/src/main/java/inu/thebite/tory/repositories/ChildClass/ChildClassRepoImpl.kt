package inu.thebite.tory.repositories.ChildClass

import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassRequest
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.retrofit.RetrofitApi
import kotlinx.coroutines.flow.Flow

class ChildClassRepoImpl: ChildClassRepo {

    private val childClassRetrofit = RetrofitApi.apiService

    override suspend fun createChildClass(selectedCenter: CenterResponse,childClass: ChildClassRequest) {
        childClassRetrofit.addClass(
            centerId = selectedCenter.id,
            AddChildClassRequest = childClass)
    }

    override suspend fun getAllChildClasses(): List<ChildClassResponse> {
        return childClassRetrofit.getAllClassList()
    }


//    override suspend fun updateChildClass(updatedChildClass: ChildClassEntity) {
//        childClassDao.updateChildClass(updatedChildClass)
//
//    }

    override suspend fun deleteChildClass(selectedCenter: CenterResponse,childClass: ChildClassResponse) {
        childClassRetrofit.deleteClass(
            centerId = selectedCenter.id,
            classId = childClass.id
        )

    }


}