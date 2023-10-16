package inu.thebite.tory.repositories.ChildClass

import inu.thebite.tory.database.ChildClass.ChildClassEntity
import kotlinx.coroutines.flow.Flow

interface ChildClassRepo {
    suspend fun createChildClass(childClass: ChildClassEntity)
    suspend fun getAllChildClasses(): Flow<List<ChildClassEntity>>
    suspend fun updateChildClass(updatedChildClass: ChildClassEntity)
    suspend fun deleteChildClass(childClass: ChildClassEntity)
}