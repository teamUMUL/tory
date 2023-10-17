package inu.thebite.tory.repositories.ChildClass

import inu.thebite.tory.model.center.CenterResponse
import inu.thebite.tory.model.childClass.ChildClassRequest
import inu.thebite.tory.model.childClass.ChildClassResponse
import kotlinx.coroutines.flow.Flow

interface ChildClassRepo {
    suspend fun createChildClass(selectedCenter: CenterResponse,childClass: ChildClassRequest)
    suspend fun getAllChildClasses(): List<ChildClassResponse>
//    suspend fun updateChildClass(updatedChildClass: ChildClassEntity)
    suspend fun deleteChildClass(selectedCenter: CenterResponse,childClass: ChildClassResponse)
}