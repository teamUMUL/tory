package inu.thebite.tory.repositories.ChildClass

import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassRequest
import inu.thebite.tory.model.childClass.ChildClassResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ChildClassRepo {
    suspend fun createChildClass(selectedCenter: CenterResponse, childClass: ChildClassRequest)
    suspend fun getAllChildClasses(): List<ChildClassResponse>

    suspend fun updateChildClass(childClass: ChildClassResponse, request: ChildClassRequest)
    suspend fun deleteChildClass(childClass: ChildClassResponse)
}