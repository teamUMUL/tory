package inu.thebite.tory.repositories.ChildInfo

import inu.thebite.tory.database.ChildInfo.ChildInfoEntity
import kotlinx.coroutines.flow.Flow

interface ChildInfoRepo {
    suspend fun createChildInfo(childInfo: ChildInfoEntity)
    suspend fun getAllChildInfos(): Flow<List<ChildInfoEntity>>
    suspend fun updateChildInfo(updatedChildInfo: ChildInfoEntity)
    suspend fun deleteChildInfo(childInfo: ChildInfoEntity)
}