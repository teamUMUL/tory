package inu.thebite.tory.repositories.ChildClass

import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassRequest
import inu.thebite.tory.model.childClass.ChildClassResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ChildClassRepo {
    suspend fun createChildClass(selectedCenter: CenterResponse, childClass: ChildClassRequest) : Response<ChildClassResponse>
    suspend fun getAllChildClasses(centerId : Long): List<ChildClassResponse>

    suspend fun updateChildClass(childClass: ChildClassResponse, request: ChildClassRequest) : Response<ChildClassResponse>
    suspend fun deleteChildClass(childClass: ChildClassResponse): Response<Boolean>
}