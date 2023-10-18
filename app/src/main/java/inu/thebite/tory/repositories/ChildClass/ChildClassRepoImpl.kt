package inu.thebite.tory.repositories.ChildClass

import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassRequest
import inu.thebite.tory.model.childClass.ChildClassResponse
import inu.thebite.tory.retrofit.RetrofitApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ChildClassRepoImpl: ChildClassRepo {

    private val childClassRetrofit = RetrofitApi.apiService

    override suspend fun createChildClass(selectedCenter: CenterResponse,childClass: ChildClassRequest) {
        childClassRetrofit.addClass(
            centerId = selectedCenter.id,
            addChildClassRequest = childClass)
    }

    override suspend fun getAllChildClasses(): List<ChildClassResponse> {
        return childClassRetrofit.getAllClassList()
    }


    override suspend fun updateChildClass(
        childClass: ChildClassResponse,
        request: ChildClassRequest
    ) {
        childClassRetrofit.updateChildClass(classId = childClass.id, updateChildClass = request)
    }

    override suspend fun deleteChildClass(childClass: ChildClassResponse) {
        childClassRetrofit.deleteClass(classId = childClass.id)
    }

}